export class PatternList {
    [key: string]: Pattern;
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

export class Pattern {
    id!: number;
    parameters!: Parameter[];
}

export class ActiveDecorator {
    name!: string;
    values!: number[]
}