import { Component, Inject } from '@angular/core';
import { OrderUpdateRequest } from '../../shared/commerce.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommerceService } from '../../shared/commerce.service';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-order-update-popup',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './order-update-popup.component.html',
  styleUrl: './order-update-popup.component.scss',
})
export class OrderUpdatePopupComponent {
  data: {
    id: number;
    name: string;
    address: string;
  };

  constructor(
    public dialogRef: MatDialogRef<OrderUpdatePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  updateOrder() {
    this.commerceService
      .updateOrder(
        new OrderUpdateRequest(this.data.name, this.data.address),
        this.data.id
      )
      .subscribe((data) => {
        if (data) {
          this.toastr.success('Order updated!');
          this.dialogRef.close();
        } else {
          this.toastr.error('Order update failed!');
        }
      });
  }

  cancel() {
    this.dialogRef.close();
  }
}
