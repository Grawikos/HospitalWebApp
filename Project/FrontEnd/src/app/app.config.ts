import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {AuthInterceptor, httpInterceptorProviders} from "./auth/auth-interceptor";
import {provideClientHydration} from "@angular/platform-browser";
export const appConfig: ApplicationConfig = {
  // providers: [provideRouter(routes)]
  providers: [provideRouter(routes), provideClientHydration(), provideHttpClient(),
    provideHttpClient(withInterceptorsFromDi(),),
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}]
};
