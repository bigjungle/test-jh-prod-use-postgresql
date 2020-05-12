import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';

type EntityResponseType = HttpResponse<IRegnRevoSdmSuffix>;
type EntityArrayResponseType = HttpResponse<IRegnRevoSdmSuffix[]>;

@Injectable({ providedIn: 'root' })
export class RegnRevoSdmSuffixService {
  public resourceUrl = SERVER_API_URL + 'api/regn-revos';

  constructor(protected http: HttpClient) {}

  create(regnRevo: IRegnRevoSdmSuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regnRevo);
    return this.http
      .post<IRegnRevoSdmSuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(regnRevo: IRegnRevoSdmSuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regnRevo);
    return this.http
      .put<IRegnRevoSdmSuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegnRevoSdmSuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegnRevoSdmSuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(regnRevo: IRegnRevoSdmSuffix): IRegnRevoSdmSuffix {
    const copy: IRegnRevoSdmSuffix = Object.assign({}, regnRevo, {
      revokeStart: regnRevo.revokeStart && regnRevo.revokeStart.isValid() ? regnRevo.revokeStart.format(DATE_FORMAT) : undefined,
      revokeOver: regnRevo.revokeOver && regnRevo.revokeOver.isValid() ? regnRevo.revokeOver.format(DATE_FORMAT) : undefined,
      punishTime: regnRevo.punishTime && regnRevo.punishTime.isValid() ? regnRevo.punishTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.revokeStart = res.body.revokeStart ? moment(res.body.revokeStart) : undefined;
      res.body.revokeOver = res.body.revokeOver ? moment(res.body.revokeOver) : undefined;
      res.body.punishTime = res.body.punishTime ? moment(res.body.punishTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((regnRevo: IRegnRevoSdmSuffix) => {
        regnRevo.revokeStart = regnRevo.revokeStart ? moment(regnRevo.revokeStart) : undefined;
        regnRevo.revokeOver = regnRevo.revokeOver ? moment(regnRevo.revokeOver) : undefined;
        regnRevo.punishTime = regnRevo.punishTime ? moment(regnRevo.punishTime) : undefined;
      });
    }
    return res;
  }
}
