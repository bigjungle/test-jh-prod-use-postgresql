import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

type EntityResponseType = HttpResponse<IRegnCancSdmSuffix>;
type EntityArrayResponseType = HttpResponse<IRegnCancSdmSuffix[]>;

@Injectable({ providedIn: 'root' })
export class RegnCancSdmSuffixService {
  public resourceUrl = SERVER_API_URL + 'api/regn-cancs';

  constructor(protected http: HttpClient) {}

  create(regnCanc: IRegnCancSdmSuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regnCanc);
    return this.http
      .post<IRegnCancSdmSuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(regnCanc: IRegnCancSdmSuffix): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(regnCanc);
    return this.http
      .put<IRegnCancSdmSuffix>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegnCancSdmSuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegnCancSdmSuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(regnCanc: IRegnCancSdmSuffix): IRegnCancSdmSuffix {
    const copy: IRegnCancSdmSuffix = Object.assign({}, regnCanc, {
      cancellationTime:
        regnCanc.cancellationTime && regnCanc.cancellationTime.isValid() ? regnCanc.cancellationTime.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.cancellationTime = res.body.cancellationTime ? moment(res.body.cancellationTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((regnCanc: IRegnCancSdmSuffix) => {
        regnCanc.cancellationTime = regnCanc.cancellationTime ? moment(regnCanc.cancellationTime) : undefined;
      });
    }
    return res;
  }
}
