import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRegnCancSdmSuffix, RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';
import { RegnCancSdmSuffixService } from './regn-canc-sdm-suffix.service';
import { RegnCancSdmSuffixComponent } from './regn-canc-sdm-suffix.component';
import { RegnCancSdmSuffixDetailComponent } from './regn-canc-sdm-suffix-detail.component';
import { RegnCancSdmSuffixUpdateComponent } from './regn-canc-sdm-suffix-update.component';

@Injectable({ providedIn: 'root' })
export class RegnCancSdmSuffixResolve implements Resolve<IRegnCancSdmSuffix> {
  constructor(private service: RegnCancSdmSuffixService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegnCancSdmSuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((regnCanc: HttpResponse<RegnCancSdmSuffix>) => {
          if (regnCanc.body) {
            return of(regnCanc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RegnCancSdmSuffix());
  }
}

export const regnCancRoute: Routes = [
  {
    path: '',
    component: RegnCancSdmSuffixComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnCanc.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegnCancSdmSuffixDetailComponent,
    resolve: {
      regnCanc: RegnCancSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnCanc.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegnCancSdmSuffixUpdateComponent,
    resolve: {
      regnCanc: RegnCancSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnCanc.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegnCancSdmSuffixUpdateComponent,
    resolve: {
      regnCanc: RegnCancSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnCanc.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
