import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestProdDatabaseSharedModule } from 'app/shared/shared.module';
import { RegnRevoSdmSuffixComponent } from './regn-revo-sdm-suffix.component';
import { RegnRevoSdmSuffixDetailComponent } from './regn-revo-sdm-suffix-detail.component';
import { RegnRevoSdmSuffixUpdateComponent } from './regn-revo-sdm-suffix-update.component';
import { RegnRevoSdmSuffixDeleteDialogComponent } from './regn-revo-sdm-suffix-delete-dialog.component';
import { regnRevoRoute } from './regn-revo-sdm-suffix.route';

@NgModule({
  imports: [TestProdDatabaseSharedModule, RouterModule.forChild(regnRevoRoute)],
  declarations: [
    RegnRevoSdmSuffixComponent,
    RegnRevoSdmSuffixDetailComponent,
    RegnRevoSdmSuffixUpdateComponent,
    RegnRevoSdmSuffixDeleteDialogComponent
  ],
  entryComponents: [RegnRevoSdmSuffixDeleteDialogComponent]
})
export class TestProdDatabaseRegnRevoSdmSuffixModule {}
