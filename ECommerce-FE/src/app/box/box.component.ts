import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { CartItemsService } from '../cart-items/shared/cart-items.service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-box',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './box.component.html',
  styleUrl: './box.component.scss',
})
export class BoxComponent implements OnInit {
  ifNotAdded: boolean = true;
  ifAdded: boolean = false;
  quantityOfProduct = 0;
  quantityOfSingleProduct: number = 0;
  environment = environment;

  @Input() prod: any;
  @Input() index: number = 0;

  constructor(private cartItemsService: CartItemsService) {}

  ngOnInit(): void {
    this.quantityOfProduct = this.prod.quantity;

    this.cartItemsService.productQuantity$.subscribe((productQuantityMap) => {
      this.quantityOfSingleProduct = productQuantityMap.get(this.index) || 0;
    });

    this.cartItemsService.deletedProduct$.subscribe((data) => {
      if (this.ifAdded && data.deleted && data.index === this.index) {
        this.ifNotAdded = data.deleted;
        this.ifAdded = !data.deleted;
      }
    });
  }

  addItemToCart(event: Event) {
    event.stopPropagation();

    this.cartItemsService.addProductToCart(this.prod, this.index);
    this.ifNotAdded = false;
    this.ifAdded = true;
  }

  decreaseProductQuantity(event: Event) {
    event.stopPropagation();

    this.cartItemsService.decreaseProductQuantity(this.index);
  }

  increaseProductQuantity(event: Event) {
    event.stopPropagation();

    this.cartItemsService.increaseProductQuantity(this.index);
  }
}
