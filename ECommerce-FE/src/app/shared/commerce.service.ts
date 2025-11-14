import { Injectable } from '@angular/core';
import {
  ImageProductResponse,
  OrderCreateRequest,
  OrderResponse,
  OrderUpdateRequest,
  ProductRequest,
  ProductResponse,
} from './commerce.model';
import { HttpClient } from '@angular/common/http';
import { ECommerceApi } from './commerce-api.constant';
import { map } from 'rxjs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommerceService {
  constructor(private http: HttpClient) {}

  // ***** Login and logout for administrator *****

  private isLoggedIn = false;
  private username_: string = '';

  login(username: string, password: string) {
    if (username === 'Belmin' && password === '1111') {
      this.isLoggedIn = true;
      this.username_ = username;
      return 200;
    } else {
      return 403;
    }
  }

  logout(): void {
    this.isLoggedIn = false;
    this.username_ = '';
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }

  getUsername(): string {
    return this.username_;
  }

  // ***** API's *****

  createProduct(productRequest: ProductRequest): Observable<ProductResponse> {
    return this.http
      .post<any>(ECommerceApi.CREATE_PRODUCT, productRequest)
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  uploadImage(image: FormData, id: number): Observable<any> {
    return this.http
      .put<any>(
        ECommerceApi.UPLOAD_IMAGE_PRODUCT.replace('#id', '' + id),
        image,
        {
          observe: 'response',
        }
      )
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  updateProduct(
    updateRequest: ProductRequest,
    id: number
  ): Observable<ProductResponse> {
    return this.http
      .put<any>(
        ECommerceApi.UPDATE_PRODUCT.replace('#id', '' + id),
        updateRequest
      )
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  getProducts(): Observable<ProductResponse[]> {
    return this.http.get<any>(ECommerceApi.GET_PRODUCTS).pipe(
      map((response) => {
        return response;
      })
    );
  }

  getProduct(id: number): Observable<ProductResponse> {
    return this.http
      .get<any>(ECommerceApi.GET_PRODUCT.replace('#id', '' + id))
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  getImage(id: number): Observable<ImageProductResponse> {
    return this.http
      .get<any>(ECommerceApi.GET_IMAGE_PRODUCT.replace('#id', '' + id))
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  getProductsByType(productType: string): Observable<ProductResponse[]> {
    const queryParams = { type: productType };
    return this.http
      .get<any>(ECommerceApi.GET_PRODUCTS_BY_TYPE, { params: queryParams })
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  getProductsByName(productName: string): Observable<ProductResponse[]> {
    const queryParams = { name: productName };
    return this.http
      .get<any>(ECommerceApi.GET_PRODUCTS_BY_NAME, { params: queryParams })
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  deleteProduct(id: number): Observable<any> {
    return this.http
      .delete(ECommerceApi.DELETE_PRODUCT.replace('#id', '' + id), {
        observe: 'response',
      })
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  createOrder(order: OrderCreateRequest): Observable<OrderResponse> {
    return this.http.post<any>(ECommerceApi.CREATE_ORDER, order).pipe(
      map((response) => {
        return response;
      })
    );
  }

  updateOrder(
    order: OrderUpdateRequest,
    id: number
  ): Observable<OrderResponse> {
    return this.http
      .put<any>(ECommerceApi.UPDATE_ORDER.replace('#id', '' + id), order)
      .pipe(
        map((response) => {
          return response;
        })
      );
  }

  getOrders(): Observable<OrderResponse[]> {
    return this.http.get<any>(ECommerceApi.GET_ORDERS).pipe(
      map((response) => {
        return response;
      })
    );
  }

  getOrder(id: number) {
    return this.http.get(ECommerceApi.GET_ORDER.replace('#id', '' + id)).pipe(
      map((response) => {
        return response;
      })
    );
  }

  getOrderByName(name: string): Observable<OrderResponse[]> {
    const queryParams = { name: name };
    console.log(queryParams);
    return this.http
      .get<any>(ECommerceApi.GET_ORDERS_BY_NAME, { params: queryParams })
      .pipe(
        map((response) => {
          console.log(response);
          return response;
        })
      );
  }

  deleteOrder(id: number): Observable<any> {
    return this.http
      .delete(ECommerceApi.DELETE_ORDER.replace('#id', '' + id), {
        observe: 'response',
      })
      .pipe(
        map((response) => {
          return response;
        })
      );
  }
}
