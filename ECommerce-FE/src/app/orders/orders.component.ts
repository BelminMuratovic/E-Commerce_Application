import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import { OrderResponse } from '../shared/commerce.model';
import { CommerceService } from '../shared/commerce.service';
import { ToastrService } from 'ngx-toastr';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { OrderUpdatePopupComponent } from './order-update-popup/order-update-popup.component';
import { OrderDeletePopupComponent } from './order-delete-popup/order-delete-popup.component';
import { OrderPreviewPopupComponent } from './order-preview-popup/order-preview-popup.component';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, HeaderComponent, ReactiveFormsModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss',
})
export class OrdersComponent implements OnInit {
  searchControl = new FormControl('');
  orders: OrderResponse[] = [];

  constructor(
    private commerceService: CommerceService,
    public dialog: MatDialog,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.getAllOrders();

    this.searchControl.valueChanges.subscribe((value) => {
      if (value) {
        value = value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
        const orderByName$ = this.commerceService.getOrderByName(value);

        orderByName$.subscribe((response) => {
          this.orders = response;
        });
      } else {
        this.getAllOrders();
      }
    });
  }

  getAllOrders() {
    this.commerceService.getOrders().subscribe((data: OrderResponse[]) => {
      this.orders = data;
    });
  }

  preview(index: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '50%';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(OrderPreviewPopupComponent, {
      ...dialogConfig,
      data: {
        products: this.orders[index].products,
      },
    });
  }

  edit(index: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '400px';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(OrderUpdatePopupComponent, {
      ...dialogConfig,
      data: {
        id: this.orders[index].id,
        name: this.orders[index].name,
        address: this.orders[index].address,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getAllOrders();
    });
  }

  delete(index: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '350px';
    dialogConfig.panelClass = 'container';

    const dialogRef = this.dialog.open(OrderDeletePopupComponent, {
      ...dialogConfig,
      data: {
        id: this.orders[index].id,
        product: this.orders[index].name,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.getAllOrders();
    });
  }
}
