import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { OrdersComponent } from './orders/orders.component';
import { ProductsComponent } from './products/products.component';
import { ShippingComponent } from './shipping/shipping.component';
import { CartItemsComponent } from './cart-items/cart-items.component';
import { AuthGuardService } from './shared/auth-guard.service';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'home', component: HomeComponent },
      {
        path: 'orders',
        component: OrdersComponent,
        canActivate: [AuthGuardService],
      },
      {
        path: 'products',
        component: ProductsComponent,
        canActivate: [AuthGuardService],
      },
      { path: 'shipping', component: ShippingComponent },
      { path: 'cartItems', component: CartItemsComponent },
    ],
  },

  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutes {}
