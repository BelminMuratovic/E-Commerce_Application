import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { BoxComponent } from '../box/box.component';
import { CommonModule } from '@angular/common';
import { CartItemsService } from '../cart-items/shared/cart-items.service';
import { CommerceService } from '../shared/commerce.service';
import { ProductDisplay, ProductResponse } from '../shared/commerce.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, HeaderComponent, BoxComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  products: ProductDisplay[] = [];

  retrievedImage: string = '';
  base64Data: any;
  retrieveResonse: any;

  constructor(
    public cartItemsService: CartItemsService,
    private commerceService: CommerceService
  ) {}

  ngOnInit() {
    this.getAllCategories();
  }

  getAllCategories() {
    this.products.splice(0, this.products.length);

    this.commerceService.getProducts().subscribe((data: ProductResponse[]) => {
      for (let i = 0; i < data.length; ++i) {
        this.commerceService.getImage(data[i].id).subscribe((res) => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

          const newProduct = new ProductDisplay(
            data[i].id,
            data[i].type,
            data[i].name,
            data[i].quantity,
            data[i].price,
            this.retrievedImage
          );

          this.products.push(newProduct);
        });
      }
    });
  }

  getByType(productType: string) {
    this.products.splice(0, this.products.length);

    this.commerceService
      .getProductsByType(productType)
      .subscribe((data: ProductResponse[]) => {
        const requests = data.map((product) =>
          this.commerceService.getImage(product.id)
        );

        forkJoin(requests).subscribe((responses: any[]) => {
          for (let i = 0; i < data.length; ++i) {
            const res = responses[i];
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

            const newProduct = new ProductDisplay(
              data[i].id,
              data[i].type,
              data[i].name,
              data[i].quantity,
              data[i].price,
              this.retrievedImage
            );

            this.products.push(newProduct);
          }
        });
      });
  }
}
