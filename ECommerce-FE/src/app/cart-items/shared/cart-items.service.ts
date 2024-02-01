import { Injectable } from '@angular/core';
import { Product, ProductResponse } from '../../shared/commerce.model';
import { BehaviorSubject } from 'rxjs';
import { CommerceService } from '../../shared/commerce.service';

@Injectable({
  providedIn: 'root',
})
export class CartItemsService {
  constructor(private commerceService: CommerceService) {}

  products: Map<number, Product> = new Map<number, Product>();

  // ***** Getting products of cart

  private productsSubject = new BehaviorSubject<Map<number, Product>>(
    new Map<number, Product>()
  );
  products$ = this.productsSubject.asObservable();

  updateProducts(productsMap: Map<number, Product>) {
    if (productsMap) {
      const map = productsMap;

      this.productsSubject.next(map);
    }
  }

  calculateTotal(): number {
    let total = 0;

    for (let product of this.products) {
      total += product[1].price * product[1].quantity;
    }
    return total;
  }

  // ***** Getting quantity of cart items size *****

  cartItemsSize: number = 0;

  private cartItemsSizeSubject: BehaviorSubject<number> =
    new BehaviorSubject<number>(0);
  cartItemsSize$ = this.cartItemsSizeSubject.asObservable();

  // ***** Getting quantity of single product *****

  private productQuantitySubject: BehaviorSubject<Map<number, number>> =
    new BehaviorSubject<Map<number, number>>(new Map<number, number>());
  productQuantity$ = this.productQuantitySubject.asObservable();

  getProductQuantity(index: number): number {
    const productQuantityMap = this.productQuantitySubject.value;
    return productQuantityMap.get(index) || 0;
  }

  private updateProductQuantity(index: number, quantity: number) {
    const productQuantityMap = new Map<number, number>(
      this.productQuantitySubject.value
    );
    productQuantityMap.set(index, quantity);
    this.productQuantitySubject.next(productQuantityMap);
  }

  // ***** Deleted product *****

  private deletedProductSubject = new BehaviorSubject<{
    index: number;
    deleted: boolean;
  }>({ index: -1, deleted: false });
  deletedProduct$ = this.deletedProductSubject.asObservable();

  // ***** Methods for adjusting the size of the cart *****

  addProductToCart(product: ProductResponse, index: number) {
    let newProduct = new Product(
      product.id,
      product.type,
      product.name,
      1,
      product.price
    );
    this.products.set(index, newProduct);
    this.updateProductQuantity(index, newProduct.quantity);
    this.updateProducts(this.products);
    this.callingResizeShoppingCart();
  }

  clearCart() {
    this.products.clear();
    this.callingResizeShoppingCart();
  }

  resizeShopingCart(): number {
    let quantity: number = 0;

    this.products.forEach((product) => {
      quantity += product.quantity;
    });

    return quantity;
  }

  callingResizeShoppingCart() {
    this.cartItemsSize = this.resizeShopingCart();
    this.cartItemsSizeSubject.next(this.cartItemsSize);
  }

  decreaseProductQuantity(index: number) {
    const product = this.products.get(index);

    if (product && product.quantity > 0) {
      product.quantity--;
      this.updateProductQuantity(index, product.quantity);
    }

    if (product && product.quantity === 0) {
      this.products.delete(index);
      this.deletedProductSubject.next({ index, deleted: true });
    }

    this.callingResizeShoppingCart();
  }

  increaseProductQuantity(index: number) {
    const product = this.products.get(index);

    if (product) {
      product.quantity++;
      this.updateProductQuantity(index, product.quantity);
    }

    this.callingResizeShoppingCart();
  }
}
