import {ActivatedRouteSnapshot, CanActivate, Route, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../../model/User";
import {AuthenticationService} from "../../services/authentication.service";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class AuthenticationGuard implements CanActivate{

  private currentUser:User = new User();

  constructor(private authenticationService: AuthenticationService, private router: Router) {
    authenticationService.currentUser.subscribe(data =>
    {
      this.currentUser = data;
    });
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
    : Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if(this.currentUser?.id)
    {
      if(route.data['roles']?.indexOf(this.currentUser.role) === -1)
      {
        this.router.navigate(["/401"])
        return false;
      }
      return true;
    }

    this.router.navigate(["/login"]);
    return false;
  }

}
