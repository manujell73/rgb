export class PatternList {
    [key: string]: Parameter[];
}

export class Parameter {
    id!: string;
    name!: string;
    type!: string;
    value: any = 0;
    minValue: number | undefined;
    maxValue: number | undefined;
    enums: string[] | undefined;
}