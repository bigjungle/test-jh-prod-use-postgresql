import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRegnRevoSdmSuffix, RegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';
import { RegnRevoSdmSuffixService } from './regn-revo-sdm-suffix.service';
import { RegnRevoSdmSuffixComponent } from './regn-revo-sdm-suffix.component';
import { RegnRevoSdmSuffixDetailComponent } from './regn-revo-sdm-suffix-detail.component';
import { RegnRevoSdmSuffixUpdateComponent } from './regn-revo-sdm-suffix-update.component';

@Injectable({ providedIn: 'root' })
export class RegnRevoSdmSuffixResolve implements Resolve<IRegnRevoSdmSuffix> {
  constructor(private service: RegnRevoSdmSuffixService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegnRevoSdmSuffix> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((regnRevo: HttpResponse<RegnRevoSdmSuffix>) => {
          if (regnRevo.body) {
            return of(regnRevo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RegnRevoSdmSuffix());
  }
}

export const regnRevoRoute: Routes = [
  {
    path: '',
    component: RegnRevoSdmSuffixComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnRevo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegnRevoSdmSuffixDetailComponent,
    resolve: {
      regnRevo: RegnRevoSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnRevo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegnRevoSdmSuffixUpdateComponent,
    resolve: {
      regnRevo: RegnRevoSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnRevo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegnRevoSdmSuffixUpdateComponent,
    resolve: {
      regnRevo: RegnRevoSdmSuffixResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testProdDatabaseApp.regnRevo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
