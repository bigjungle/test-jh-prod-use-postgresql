import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'regn-canc-sdm-suffix',
        loadChildren: () =>
          import('./regn-canc-sdm-suffix/regn-canc-sdm-suffix.module').then(m => m.TestProdDatabaseRegnCancSdmSuffixModule)
      },
      {
        path: 'regn-revo-sdm-suffix',
        loadChildren: () =>
          import('./regn-revo-sdm-suffix/regn-revo-sdm-suffix.module').then(m => m.TestProdDatabaseRegnRevoSdmSuffixModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TestProdDatabaseEntityModule {}
