import { Component, HostListener, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CartItemsService } from './shared/cart-items.service';
import { CommerceService } from '../shared/commerce.service';

@Component({
  selector: 'app-cart-items',
  standalone: true,
  imports: [CommonModule, HeaderComponent],
  templateUrl: './cart-items.component.html',
  styleUrl: './cart-items.component.scss',
})
export class CartItemsComponent implements OnInit {
  cartItemsSize: number = 0;
  quantityOfProduct: number = 0;

  constructor(
    private router: Router,
    public cartItemsService: CartItemsService,
    private commerceService: CommerceService
  ) {}

  ngOnInit(): void {
    this.cartItemsService.callingResizeShoppingCart();
    this.cartItemsSize = this.cartItemsService.cartItemsSize;
  }

  decreaseProductQuantity(id: number) {
    this.cartItemsService.decreaseProductQuantity(id);
  }

  increaseProductQuantity(id: number) {
    this.cartItemsService.increaseProductQuantity(id);
  }

  clearCart() {
    this.cartItemsService.clearCart();
  }

  checkOut() {
    this.router.navigate(['shipping']);
  }
}
