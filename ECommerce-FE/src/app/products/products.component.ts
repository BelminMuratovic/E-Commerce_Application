import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ProductResponse } from '../shared/commerce.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CommerceService } from '../shared/commerce.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { ProductUpdatePopupComponent } from './product-update-popup/product-update-popup.component';
import { ProductCreatePopupComponent } from './product-create-popup/product-create-popup.component';
import { ProductDeletePopupComponent } from './product-delete-popup/product-delete-popup.component';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, HeaderComponent, ReactiveFormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss',
})
export class ProductsComponent implements OnInit {
  products: ProductResponse[] = [];
  searchControl = new FormControl('');
  navbarfixed: boolean = false;

  @HostListener('window:scroll', ['$event']) onscroll() {
    if (window.scrollY > 100) {
      this.navbarfixed = true;
    } else {
      this.navbarfixed = false;
    }
  }

  constructor(
    private commerceService: CommerceService,
    public dialog: MatDialog,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.getAllProducts();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value) {
        value = value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
        const productsByType$ = this.commerceService.getProductsByType(value);
        const productsByName$ = this.commerceService.getProductsByName(value);

        forkJoin([productsByType$, productsByName$]).subscribe(
          ([productsByType, productsByName]) => {
            this.products = [...productsByType, ...productsByName];
          }
        );
      } else {
        this.getAllProducts();
      }
    });
  }

  getAllProducts() {
    this.commerceService.getProducts().subscribe((data: ProductResponse[]) => {
      this.products = data;
    });
  }

  async newProduct() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '450px';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(
      ProductCreatePopupComponent,
      dialogConfig
    );

    dialogRef.afterClosed().subscribe((result) => {
      this.getAllProducts();
    });
  }

  edit(index: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '450px';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(ProductUpdatePopupComponent, {
      ...dialogConfig,
      data: {
        id: this.products[index].id,
        productType: this.products[index].type,
        productName: this.products[index].name,
        productQuantity: this.products[index].quantity,
        productPrice: this.products[index].price,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getAllProducts();
    });
  }

  delete(index: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '350px';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(ProductDeletePopupComponent, {
      ...dialogConfig,
      data: {
        id: this.products[index].id,
        product: this.products[index].name,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getAllProducts();
    });
  }
}
