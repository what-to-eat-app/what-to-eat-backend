import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { User } from '../_model/user.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy{

  user!: User;
  AuthUserSub!: Subscription;

  constructor(private authService: AuthService) {
    
  }

  ngOnInit(): void {
    this.AuthUserSub = this.authService.AuthenticatedUser$.subscribe({
      next: user => {
        if (user) {
          this.user = user;
        }
      }
    })
  }

  handleLogout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.AuthUserSub.unsubscribe();
  }
}
