import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { OrdersComponent } from './orders/orders.component';
import { ProductsComponent } from './products/products.component';
import { ShippingComponent } from './shipping/shipping.component';
import { CartItemsComponent } from './cart-items/cart-items.component';
import { CommonModule } from '@angular/common';
import { AuthGuardService } from './shared/auth-guard.service';
import { CommerceService } from './shared/commerce.service';
import { BoxComponent } from './box/box.component';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    OrdersComponent,
    HomeComponent,
    HeaderComponent,
    ProductsComponent,
    ShippingComponent,
    CartItemsComponent,
    BoxComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    RouterModule.forRoot([]),
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    ReactiveFormsModule,
  ],
  providers: [AuthGuardService, CommerceService, HttpClientModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
