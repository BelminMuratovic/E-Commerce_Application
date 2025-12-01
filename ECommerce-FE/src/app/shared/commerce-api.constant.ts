export class ECommerceApi {
  public static URL = 'http://localhost:8080/api/v1';

  public static CREATE_PRODUCT = `${ECommerceApi.URL}/products`;
  public static UPDATE_PRODUCT = `${ECommerceApi.URL}/products/#id`;
  public static GET_PRODUCT = `${ECommerceApi.URL}/products/#id`;
  public static GET_PRODUCTS = `${ECommerceApi.URL}/products`;
  public static GET_PRODUCTS_BY_TYPE = `${ECommerceApi.URL}/products/byType`;
  public static GET_PRODUCTS_BY_NAME = `${ECommerceApi.URL}/products/byName`;
  public static DELETE_PRODUCT = `${ECommerceApi.URL}/products/#id`;

  public static CREATE_ORDER = `${ECommerceApi.URL}/orders`;
  public static UPDATE_ORDER = `${ECommerceApi.URL}/orders/#id`;
  public static GET_ORDER = `${ECommerceApi.URL}/orders/#id`;
  public static GET_ORDERS = `${ECommerceApi.URL}/orders`;
  public static GET_ORDERS_BY_NAME = `${ECommerceApi.URL}/orders/byName`;
  public static DELETE_ORDER = `${ECommerceApi.URL}/orders/#id`;
}
