import { Moment } from 'moment';

export interface IRegnCancSdmSuffix {
  id?: number;
  name?: string;
  descString?: string;
  orgInfo?: string;
  cancellationWay?: string;
  cancellationReason?: string;
  cancellationTime?: Moment;
  cancellationProof?: string;
  remarks?: string;
  ownerByLastName?: string;
  ownerById?: number;
}

export class RegnCancSdmSuffix implements IRegnCancSdmSuffix {
  constructor(
    public id?: number,
    public name?: string,
    public descString?: string,
    public orgInfo?: string,
    public cancellationWay?: string,
    public cancellationReason?: string,
    public cancellationTime?: Moment,
    public cancellationProof?: string,
    public remarks?: string,
    public ownerByLastName?: string,
    public ownerById?: number
  ) {}
}
