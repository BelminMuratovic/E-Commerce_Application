import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommerceService } from '../../shared/commerce.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-delete-popup',
  standalone: true,
  imports: [],
  templateUrl: './order-delete-popup.component.html',
  styleUrl: './order-delete-popup.component.scss',
})
export class OrderDeletePopupComponent {
  data = {
    id: 0,
    name: '',
  };

  constructor(
    public dialogRef: MatDialogRef<OrderDeletePopupComponent>,
    private commerceService: CommerceService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public initialData: any
  ) {
    this.data = { ...this.initialData };
  }

  yes() {
    this.commerceService.deleteOrder(this.data.id).subscribe((result) => {
      if (result) {
        this.toastr.success('Order deleted!');
        this.dialogRef.close();
      } else {
        this.toastr.error('Order deletion failed!');
      }
    });
  }

  no() {
    this.dialogRef.close();
  }
}
