import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommerceService } from '../shared/commerce.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CartItemsService } from '../cart-items/shared/cart-items.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  username = '';
  password = '';
  errorMsg = '';
  administrator = '';
  loginActivated: boolean = false;
  caretDownActivated: boolean = false;
  adminOptions: boolean = false;
  firstClickAfterOpen = false;
  adminFirstClickAfterOpen = false;
  cartItemsSize$!: Observable<number>;

  constructor(
    private router: Router,
    private commerceService: CommerceService,
    private cartItemsService: CartItemsService
  ) {}

  ngOnInit(): void {
    this.getAdmin();
    this.isAuthenticated();
    this.cartItemsSize$ = this.cartItemsService.cartItemsSize$;
  }

  getAdmin() {
    this.administrator = this.commerceService.getUsername();
  }

  isAuthenticated() {
    this.caretDownActivated = this.commerceService.isAuthenticated();
  }

  goToHome() {
    this.router.navigate(['home']);
  }

  goToCart() {
    this.router.navigate(['cartItems']);
  }

  openLoginForm() {
    this.loginActivated = true;
  }

  login() {
    if (this.username.trim().length === 0) {
      this.errorMsg = 'Username is required';
    } else if (this.password.trim().length === 0) {
      this.errorMsg = 'Password is required';
    } else {
      this.errorMsg = '';
      let res = this.commerceService.login(this.username, this.password);
      if (res === 200) {
        this.administrator = this.username;
        this.loginActivated = false;
        this.caretDownActivated = true;
      } else if (res === 403) {
        this.errorMsg = 'Invalid Credentials';
      }
    }
  }

  openAdminOptions() {
    this.adminOptions = true;
  }

  @HostListener('document:click', ['$event'])
  handleDocumentClick(event: Event) {
    const clickedElement = event.target as HTMLElement;

    if (this.loginActivated) {
      if (!this.firstClickAfterOpen) {
        this.firstClickAfterOpen = true;
        return;
      }
      if (
        !clickedElement.closest('.login-form') &&
        !clickedElement.closest('.form-group') &&
        !clickedElement.closest('.administrator')
      ) {
        this.loginActivated = false;
        this.firstClickAfterOpen = false;
      }
    }

    if (this.adminOptions) {
      if (!this.adminFirstClickAfterOpen) {
        this.adminFirstClickAfterOpen = true;
        return;
      }
      if (
        !clickedElement.closest('.custom-table-container') &&
        !clickedElement.closest('.custom-table') &&
        !clickedElement.closest('.caret')
      ) {
        this.adminOptions = false;
        this.adminFirstClickAfterOpen = false;
      }
    }
  }

  openOrders() {
    this.router.navigate(['orders']);
  }

  openProducts() {
    this.router.navigate(['products']);
  }

  logout() {
    this.commerceService.logout();
    this.router.navigate(['home']);
    this.getAdmin();
    this.isAuthenticated();
    this.adminOptions = false;
  }
}
