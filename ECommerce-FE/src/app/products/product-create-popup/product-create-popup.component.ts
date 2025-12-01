import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CommerceService } from '../../shared/commerce.service';
import { ProductRequest, ProductResponse } from '../../shared/commerce.model';
import { ToastrService } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-create-popup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './product-create-popup.component.html',
  styleUrl: './product-create-popup.component.scss',
})
export class ProductCreatePopupComponent {
  productType!: string;
  productName!: string;
  productQuantity?: Number;
  productPrice?: Number;
  selectedImage!: File;

  constructor(
    public dialogRef: MatDialogRef<ProductCreatePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService
  ) {}

  public onFileChanged(event: any) {
    this.selectedImage = event.target.files[0];
  }

  saveProduct() {
    const createProductRequest = new FormData();
    if (this.productType) {
      createProductRequest.append('type', this.productType);
    }

    if (this.productName) {
      createProductRequest.append('name', this.productName);
    }

    if (this.productQuantity) {
      createProductRequest.append('quantity', this.productQuantity.toString());
    }

    if (this.productPrice) {
      createProductRequest.append('price', this.productPrice.toString());
    }
    createProductRequest.append('image', this.selectedImage);

    this.commerceService.createProduct(createProductRequest).subscribe({
      next: (data: ProductResponse) => {
        this.toastr.success('Product created!');
        this.dialogRef.close();
      },
      error: (error) => {
        if (error.status === 400) {
          this.toastr.error('Please check all required fields.');
        } else {
          this.toastr.error('Product creation failed!');
        }
      },
    });
  }

  cancel() {
    this.dialogRef.close();
  }
}
