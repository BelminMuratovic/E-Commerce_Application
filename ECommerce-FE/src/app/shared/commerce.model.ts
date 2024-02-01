import { SafeUrl } from "@angular/platform-browser";

export class Product {
  id: number;
  type: string;
  name: string;
  quantity: number;
  price: number;
  constructor(
    id: number,
    type: string,
    name: string,
    quantity: number,
    price: number
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}

export class ProductRequest {
  type?: string;
  name?: string;
  quantity?: number;
  price?: number;
  constructor(type: string, name: string, quantity: number, price: number) {
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}

export class ProductResponse {
  id: number;
  type: string;
  name: string;
  quantity: number;
  price: number;
  constructor(
    id: number,
    type: string,
    name: string,
    quantity: number,
    price: number
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}

export class ProductDisplay {
  id: number;
  type: string;
  name: string;
  quantity: number;
  price: number;
  retrievedImage: string
  constructor(
    id: number,
    type: string,
    name: string,
    quantity: number,
    price: number,
    retrievedImage: string
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.retrievedImage = retrievedImage;
  }
}

export class ImageProductResponse {
  id: number;
  type: string;
  name: string;
  quantity: number;
  price: number;
  imageName: any;
  imageType: any;
  picByte: any;
  constructor(
    id: number,
    type: string,
    name: string,
    quantity: number,
    price: number,
    imageName: any,
    imageType: any,
    picByte: any
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.imageName = imageName;
    this.imageType = imageType;
    this.picByte = picByte;
  }
}

export class OrderCreateRequest {
  name: string;
  address: string;
  products: ProductResponse[];
  constructor(name: string, address: string, products: ProductResponse[]) {
    this.name = name;
    this.address = address;
    this.products = products;
  }
}

export class OrderUpdateRequest {
  name: string;
  address: string;
  constructor(name: string, address: string) {
    this.name = name;
    this.address = address;
  }
}

export class OrderResponse {
  id: number;
  name: string;
  address: string;
  date: Date;
  products: ProductResponse[];
  constructor(
    id: number,
    name: string,
    address: string,
    date: Date,
    products: ProductResponse[]
  ) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.date = date;
    this.products = products;
  }
}
