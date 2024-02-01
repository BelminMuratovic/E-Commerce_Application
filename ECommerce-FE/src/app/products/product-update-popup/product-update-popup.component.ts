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
    productImage: '',
  };

  constructor(
    public dialogRef: MatDialogRef<ProductUpdatePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  updateProduct() {
    const updateProduct = new ProductRequest(
      this.data.productType,
      this.data.productName,
      this.data.productQuantity,
      this.data.productPrice
    );

    this.commerceService
      .updateProduct(updateProduct, this.data.id)
      .subscribe((data: ProductResponse) => {
        if (data) {
          this.toastr.success('Product updated!');
          this.dialogRef.close();
        } else {
          this.toastr.error('Product update failed!');
        }
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
