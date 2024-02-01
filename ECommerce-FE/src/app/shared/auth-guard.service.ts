import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { CommerceService } from './commerce.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService {
  constructor(
    private comemerceService: CommerceService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.comemerceService.isAuthenticated()) {
      return true;
    } else {
      this.router.navigate(['/home']);
      return false;
    }
  }
}
