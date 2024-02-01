export class ECommerceApi {
  public static URL = 'http://localhost:8080';

  public static CREATE_PRODUCT = `${ECommerceApi.URL}/product`;
  public static UPLOAD_IMAGE_PRODUCT = `${ECommerceApi.URL}/product/upload-image/#id`;
  public static UPDATE_PRODUCT = `${ECommerceApi.URL}/product/#id`;
  public static GET_PRODUCT = `${ECommerceApi.URL}/product/#id`;
  public static GET_IMAGE_PRODUCT = `${ECommerceApi.URL}/product/get-image/#id`;
  public static GET_PRODUCTS = `${ECommerceApi.URL}/product`;
  public static GET_PRODUCTS_BY_TYPE = `${ECommerceApi.URL}/product/byType`;
  public static GET_PRODUCTS_BY_NAME = `${ECommerceApi.URL}/product/byName`;
  public static DELETE_PRODUCT = `${ECommerceApi.URL}/product/#id`;

  public static CREATE_ORDER = `${ECommerceApi.URL}/order`;
  public static UPDATE_ORDER = `${ECommerceApi.URL}/order/#id`;
  public static GET_ORDER = `${ECommerceApi.URL}/order/#id`;
  public static GET_ORDERS = `${ECommerceApi.URL}/order`;
  public static GET_ORDERS_BY_NAME = `${ECommerceApi.URL}/order/byName`;
  public static DELETE_ORDER = `${ECommerceApi.URL}/order/#id`;
}
