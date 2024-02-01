import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommerceService } from '../../shared/commerce.service';

@Component({
  selector: 'app-product-delete-popup',
  standalone: true,
  imports: [],
  templateUrl: './product-delete-popup.component.html',
  styleUrl: './product-delete-popup.component.scss',
})
export class ProductDeletePopupComponent {
  data = {
    id: 0,
    product: '',
  };

  constructor(
    public dialogRef: MatDialogRef<ProductDeletePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  yes() {
    this.commerceService.deleteProduct(this.data.id).subscribe((result) => {
      if (result) {
        this.toastr.success('Product deleted!');
        this.dialogRef.close();
      } else {
        this.toastr.error('Product deletion failed!');
      }
    });
  }

  no() {
    this.dialogRef.close();
  }
}
