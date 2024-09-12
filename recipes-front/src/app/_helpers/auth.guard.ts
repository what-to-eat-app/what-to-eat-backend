import { state } from "@angular/animations";
import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../_services/auth.service";
import { map, take } from "rxjs";

export const authGuard: CanActivateFn = (
    route,
    state
) => {
    const router = inject(Router);
    const authService = inject(AuthService);

    return authService.AuthenticatedUser$.pipe(
        take(1), //take the first one and unsubscribe automatically
        map(user => {
            // check the route is restricted by role
            const { roles } = route.data;
            if (user && user.role && roles.includes(user.role.name)) {
                return true;
            }
            if (user) {
                console.log(user);
                return router.createUrlTree(['/forbidden']);
            }
            return router.createUrlTree(['/login']);
        })
    )
}
