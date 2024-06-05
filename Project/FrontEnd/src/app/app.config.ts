import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {AuthInterceptor, httpInterceptorProviders} from "./auth/auth-interceptor";
import {provideClientHydration} from "@angular/platform-browser";
import {provideAnimations} from "@angular/platform-browser/animations";
import {provideNativeDateAdapter} from "@angular/material/core";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
export const appConfig: ApplicationConfig = {
  // providers: [provideRouter(routes)]
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideClientHydration(), provideHttpClient(),
    provideHttpClient(withInterceptorsFromDi(),),
    provideNativeDateAdapter(),
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}, provideAnimationsAsync(), provideAnimationsAsync()]
};
