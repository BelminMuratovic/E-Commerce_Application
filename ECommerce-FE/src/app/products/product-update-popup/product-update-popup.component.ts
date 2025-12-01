import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommerceService } from '../../shared/commerce.service';
import { ToastrService } from 'ngx-toastr';
import { ProductRequest, ProductResponse } from '../../shared/commerce.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-update-popup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './product-update-popup.component.html',
  styleUrl: './product-update-popup.component.scss',
})
export class ProductUpdatePopupComponent {
  data = {
    id: 0,
    productType: '',
    productName: '',
    productQuantity: 0,
    productPrice: 0,
  };
  selectedImage!: File;

  constructor(
    public dialogRef: MatDialogRef<ProductUpdatePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  public onFileChanged(event: any) {
    this.selectedImage = event.target.files[0];
  }

  updateProduct() {
    const updateProductRequest = new FormData();
    if (this.data.productType) {
      updateProductRequest.append('type', this.data.productType);
    }

    if (this.data.productName) {
      updateProductRequest.append('name', this.data.productName);
    }

    if (this.data.productQuantity) {
      updateProductRequest.append(
        'quantity',
        this.data.productQuantity.toString()
      );
    }

    if (this.data.productPrice) {
      updateProductRequest.append('price', this.data.productPrice.toString());
    }
    updateProductRequest.append('image', this.selectedImage);

    this.commerceService
      .updateProduct(updateProductRequest, this.data.id)
      .subscribe({
        next: (data: ProductResponse) => {
          this.toastr.success('Product updated!');
          this.dialogRef.close();
        },
        error: (error) => {
          if (error.status === 400) {
            this.toastr.error('Please check all required fields.');
          } else {
            this.toastr.error('Product update failed!');
          }
        },
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
