import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestProdDatabaseSharedModule } from 'app/shared/shared.module';
import { RegnCancSdmSuffixComponent } from './regn-canc-sdm-suffix.component';
import { RegnCancSdmSuffixDetailComponent } from './regn-canc-sdm-suffix-detail.component';
import { RegnCancSdmSuffixUpdateComponent } from './regn-canc-sdm-suffix-update.component';
import { RegnCancSdmSuffixDeleteDialogComponent } from './regn-canc-sdm-suffix-delete-dialog.component';
import { regnCancRoute } from './regn-canc-sdm-suffix.route';

@NgModule({
  imports: [TestProdDatabaseSharedModule, RouterModule.forChild(regnCancRoute)],
  declarations: [
    RegnCancSdmSuffixComponent,
    RegnCancSdmSuffixDetailComponent,
    RegnCancSdmSuffixUpdateComponent,
    RegnCancSdmSuffixDeleteDialogComponent
  ],
  entryComponents: [RegnCancSdmSuffixDeleteDialogComponent]
})
export class TestProdDatabaseRegnCancSdmSuffixModule {}
