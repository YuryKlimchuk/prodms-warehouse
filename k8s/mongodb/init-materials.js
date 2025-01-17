// Сталь 45
db.materials.insertMany([
    {
        number: 'Steel-45_GOST',
        groupNumber: 'Steel-45',
        type: 1,
        name: 'Сталь 45 ГОСТ4543-2016',
        groupName: 'Сталь 45',
        size: 27,
        profile: 1,
        standard: 'ГОСТ4543-2016',
        measureUnit: 1,
        count: Double(515.0)
    },
    // Сталь 20X
    {
        number: 'Steel-20X_GOST',
        groupNumber: 'Steel-20X',
        type: 1,
        name: 'Сталь 20Х ГОСТ4543-2016',
        groupName: 'Сталь 20Х',
        size: 32,
        profile: 1,
        standard: 'ГОСТ4543-2016',
        measureUnit: 1,
        count: Double(780.0)
    },
    // Сталь 40X
    {
        number: 'Steel-40X_GOST',
        groupNumber: 'Steel-40X',
        type: 1,
        name: 'Сталь 40Х ГОСТ4543-2016',
        groupName: 'Сталь 40Х',
        size: 22,
        profile: 1,
        standard: 'ГОСТ4543-2016',
        measureUnit: 1,
        count: Double(1000.0)
    }
]);