import { Component, HostListener, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import {
  OrderCreateRequest,
  OrderResponse,
  ProductRequest,
  ProductResponse,
} from '../shared/commerce.model';
import { CommonModule } from '@angular/common';
import { CartItemsService } from '../cart-items/shared/cart-items.service';
import { FormsModule } from '@angular/forms';
import { CommerceService } from '../shared/commerce.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-shipping',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent],
  templateUrl: './shipping.component.html',
  styleUrl: './shipping.component.scss',
})
export class ShippingComponent implements OnInit {
  products: Map<number, ProductResponse> = new Map<number, ProductResponse>();
  orderedProducts: ProductResponse[] = [];

  shippingData = {
    name: '',
    address: '',
  };

  newOrder: boolean = true;
  ordered: boolean = false;

  cartItemsSize: number = 0;
  navbarfixed: boolean = false;

  @HostListener('window:scroll', ['$event']) onscroll() {
    if (window.scrollY > 100) {
      this.navbarfixed = true;
    } else {
      this.navbarfixed = false;
    }
  }

  constructor(
    public cartItemsService: CartItemsService,
    private commerceService: CommerceService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.cartItemsService.products$.subscribe((prod) => {
      this.products = prod;
    });

    this.cartItemsService.cartItemsSize$.subscribe((size) => {
      this.cartItemsSize = size;
    });
  }

  moveProductsToArray() {
    for (let product of this.products) {
      this.orderedProducts.push(
        new ProductResponse(
          product[1].id,
          product[1].type,
          product[1].name,
          product[1].quantity,
          product[1].price
        )
      );
    }
  }

  placeOrder() {
    this.moveProductsToArray();

    let createOrder = new OrderCreateRequest(
      this.shippingData.name,
      this.shippingData.address,
      this.orderedProducts
    );

    this.commerceService
      .createOrder(createOrder)
      .subscribe((data: OrderResponse) => {
        if (data) {
          this.toastr.success('Order created!');
          this.newOrder = false;
          this.ordered = true;
        } else {
          this.toastr.error('Order creation failed!');
        }
      });

    for (let product of this.orderedProducts) {
      this.commerceService.getProduct(product.id).subscribe((data) => {
        const updateRequest = new ProductRequest(
          product.type,
          product.name,
          data.quantity - product.quantity,
          product.price
        );

        this.commerceService
          .updateProduct(updateRequest, product.id)
          .subscribe((data) => {
            console.log(data);
          });
      });
    }

    this.cartItemsService.clearCart();
  }
}
