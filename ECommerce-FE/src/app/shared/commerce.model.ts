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
  type: string;
  name: string;
  quantity: number;
  price: number;
  image: File;
  constructor(
    type: string,
    name: string,
    quantity: number,
    price: number,
    image: File
  ) {
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.image = image;
  }
}

export class ProductResponse {
  id: number;
  type: string;
  name: string;
  quantity: number;
  price: number;
  image: string;
  constructor(
    id: number,
    type: string,
    name: string,
    quantity: number,
    price: number,
    image: string
  ) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.image = image;
  }
}

export class OrderCreateRequest {
  name: string;
  address: string;
  products: Product[];
  constructor(name: string, address: string, products: Product[]) {
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
