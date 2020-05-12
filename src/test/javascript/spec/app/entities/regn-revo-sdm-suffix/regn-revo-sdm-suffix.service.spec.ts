import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RegnRevoSdmSuffixService } from 'app/entities/regn-revo-sdm-suffix/regn-revo-sdm-suffix.service';
import { IRegnRevoSdmSuffix, RegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';

describe('Service Tests', () => {
  describe('RegnRevoSdmSuffix Service', () => {
    let injector: TestBed;
    let service: RegnRevoSdmSuffixService;
    let httpMock: HttpTestingController;
    let elemDefault: IRegnRevoSdmSuffix;
    let expectedResult: IRegnRevoSdmSuffix | IRegnRevoSdmSuffix[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RegnRevoSdmSuffixService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RegnRevoSdmSuffix(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            revokeStart: currentDate.format(DATE_FORMAT),
            revokeOver: currentDate.format(DATE_FORMAT),
            punishTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RegnRevoSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            revokeStart: currentDate.format(DATE_FORMAT),
            revokeOver: currentDate.format(DATE_FORMAT),
            punishTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokeStart: currentDate,
            revokeOver: currentDate,
            punishTime: currentDate
          },
          returnedFromService
        );

        service.create(new RegnRevoSdmSuffix()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RegnRevoSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descString: 'BBBBBB',
            orgInfo: 'BBBBBB',
            revokeTimeSpan: 1,
            revokeStart: currentDate.format(DATE_FORMAT),
            revokeOver: currentDate.format(DATE_FORMAT),
            punishOrg: 'BBBBBB',
            punishTime: currentDate.format(DATE_TIME_FORMAT),
            facts: 'BBBBBB',
            autoProcess: true,
            revokeProof: 'BBBBBB',
            remarks: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokeStart: currentDate,
            revokeOver: currentDate,
            punishTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RegnRevoSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descString: 'BBBBBB',
            orgInfo: 'BBBBBB',
            revokeTimeSpan: 1,
            revokeStart: currentDate.format(DATE_FORMAT),
            revokeOver: currentDate.format(DATE_FORMAT),
            punishOrg: 'BBBBBB',
            punishTime: currentDate.format(DATE_TIME_FORMAT),
            facts: 'BBBBBB',
            autoProcess: true,
            revokeProof: 'BBBBBB',
            remarks: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokeStart: currentDate,
            revokeOver: currentDate,
            punishTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RegnRevoSdmSuffix', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
