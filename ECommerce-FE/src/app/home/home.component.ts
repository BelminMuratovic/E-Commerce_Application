import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { BoxComponent } from '../box/box.component';
import { CommonModule } from '@angular/common';
import { CartItemsService } from '../cart-items/shared/cart-items.service';
import { CommerceService } from '../shared/commerce.service';
import { ProductResponse } from '../shared/commerce.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, HeaderComponent, BoxComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  products: ProductResponse[] = [];

  constructor(
    public cartItemsService: CartItemsService,
    private commerceService: CommerceService
  ) {}

  ngOnInit() {
    this.getAllCategories();
  }

  getAllCategories() {
    this.commerceService.getProducts().subscribe((data: ProductResponse[]) => {
      this.products = data;
    });
  }

  getByType(productType: string) {
    this.commerceService
      .getProductsByType(productType)
      .subscribe((data: ProductResponse[]) => {
        this.products = data;
      });
  }
}
