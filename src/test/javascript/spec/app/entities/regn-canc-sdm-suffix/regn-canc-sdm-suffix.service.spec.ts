import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RegnCancSdmSuffixService } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix.service';
import { IRegnCancSdmSuffix, RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

describe('Service Tests', () => {
  describe('RegnCancSdmSuffix Service', () => {
    let injector: TestBed;
    let service: RegnCancSdmSuffixService;
    let httpMock: HttpTestingController;
    let elemDefault: IRegnCancSdmSuffix;
    let expectedResult: IRegnCancSdmSuffix | IRegnCancSdmSuffix[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RegnCancSdmSuffixService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RegnCancSdmSuffix(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            cancellationTime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RegnCancSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            cancellationTime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cancellationTime: currentDate
          },
          returnedFromService
        );

        service.create(new RegnCancSdmSuffix()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RegnCancSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descString: 'BBBBBB',
            orgInfo: 'BBBBBB',
            cancellationWay: 'BBBBBB',
            cancellationReason: 'BBBBBB',
            cancellationTime: currentDate.format(DATE_FORMAT),
            cancellationProof: 'BBBBBB',
            remarks: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cancellationTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RegnCancSdmSuffix', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            descString: 'BBBBBB',
            orgInfo: 'BBBBBB',
            cancellationWay: 'BBBBBB',
            cancellationReason: 'BBBBBB',
            cancellationTime: currentDate.format(DATE_FORMAT),
            cancellationProof: 'BBBBBB',
            remarks: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cancellationTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RegnCancSdmSuffix', () => {
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
