import { Component, Inject } from '@angular/core';
import { ProductResponse } from '../../shared/commerce.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-order-preview-popup',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './order-preview-popup.component.html',
  styleUrl: './order-preview-popup.component.scss',
})
export class OrderPreviewPopupComponent {
  data: {
    products: ProductResponse[];
  };

  constructor(
    public dialogRef: MatDialogRef<OrderPreviewPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  cancel() {
    this.dialogRef.close();
  }
}
