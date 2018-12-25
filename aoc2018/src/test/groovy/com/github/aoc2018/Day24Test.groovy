package com.github.aoc2018

class Day24Test extends AocSpec {
    Day24 sut = new Day24()

    def 'should pass sample tests for first task'() {
        given:
            List<Day24.Group> immuneSystem = [
                new Day24.Group(
                    1,
                    Day24.GroupType.IMMUNE_SYSTEM,
                    17,
                    5390,
                    ['radiation', 'bludgeoning'],
                    [],
                    4507,
                    'fire',
                    2
                ),
                new Day24.Group(
                    2,
                    Day24.GroupType.IMMUNE_SYSTEM,
                    989,
                    1274,
                    ['bludgeoning', 'slashing'],
                    ['fire'],
                    25,
                    'slashing',
                    3
                )
            ]
            List<Day24.Group> infection = [
                new Day24.Group(
                    1,
                    Day24.GroupType.INFECTION,
                    801,
                    4706,
                    ['radiation'],
                    [],
                    116,
                    'bludgeoning',
                    1
                ),
                new Day24.Group(
                    2,
                    Day24.GroupType.INFECTION,
                    4485,
                    2961,
                    ['fire', 'cold'],
                    ['radiation'],
                    12,
                    'slashing',
                    4
                )
            ]
        expect:
            sut.first(immuneSystem, infection) == 5216
    }

    def 'should print result for first task'() {
        given:
            println(sut.first(immuneSystem, infection))
    }

    def 'should print result for second task'() {
        given:
            println(sut.second(immuneSystem, infection))
    }

    List<Day24.Group> immuneSystem = [
        new Day24.Group(
            1,
            Day24.GroupType.IMMUNE_SYSTEM,
            2334,
            8900,
            [],
            [],
            31,
            'fire',
            4
        ),
        new Day24.Group(
            2,
            Day24.GroupType.IMMUNE_SYSTEM,
            411,
            8067,
            [],
            [],
            195,
            'radiation',
            1
        ),
        new Day24.Group(
            3,
            Day24.GroupType.IMMUNE_SYSTEM,
            449,
            9820,
            ['fire'],
            [],
            193,
            'bludgeoning',
            3
        ),
        new Day24.Group(
            4,
            Day24.GroupType.IMMUNE_SYSTEM,
            452,
            4418,
            ['fire'],
            ['bludgeoning'],
            89,
            'bludgeoning',
            10
        ),
        new Day24.Group(
            5,
            Day24.GroupType.IMMUNE_SYSTEM,
            858,
            5016,
            ['bludgeoning', 'fire'],
            ['slashing'],
            58,
            'bludgeoning',
            18
        ),
        new Day24.Group(
            6,
            Day24.GroupType.IMMUNE_SYSTEM,
            3049,
            9940,
            [],
            [],
            29,
            'cold',
            12
        ),
        new Day24.Group(
            7,
            Day24.GroupType.IMMUNE_SYSTEM,
            610,
            7021,
            ['bludgeoning', 'radiation'],
            [],
            114,
            'fire',
            7
        ),
        new Day24.Group(
            8,
            Day24.GroupType.IMMUNE_SYSTEM,
            4033,
            8807,
            ['radiation'],
            [],
            21,
            'cold',
            5
        ),
        new Day24.Group(
            9,
            Day24.GroupType.IMMUNE_SYSTEM,
            1209,
            7468,
            ['fire'],
            ['cold'],
            50,
            'radiation',
            20
        ),
        new Day24.Group(
            10,
            Day24.GroupType.IMMUNE_SYSTEM,
            3228,
            7550,
            ['cold'],
            ['bludgeoning', 'radiation'],
            21,
            'slashing',
            14
        )
    ]

    List<Day24.Group> infection = [
        new Day24.Group(
            1,
            Day24.GroupType.INFECTION,
            1230,
            36915,
            ['cold', 'slashing'],
            [],
            58,
            'bludgeoning',
            16
        ),
        new Day24.Group(
            2,
            Day24.GroupType.INFECTION,
            629,
            23164,
            [],
            [],
            72,
            'slashing',
            11
        ),
        new Day24.Group(
            3,
            Day24.GroupType.INFECTION,
            266,
            16518,
            [],
            [],
            113,
            'fire',
            2
        ),
        new Day24.Group(
            4,
            Day24.GroupType.INFECTION,
            45,
            17769,
            [],
            ['radiation', 'slashing', 'bludgeoning', 'fire'],
            774,
            'fire',
            19
        ),
        new Day24.Group(
            5,
            Day24.GroupType.INFECTION,
            93,
            32105,
            ['fire'],
            [],
            535,
            'fire',
            8
        ),
        new Day24.Group(
            6,
            Day24.GroupType.INFECTION,
            957,
            19599,
            [],
            ['cold'],
            32,
            'cold',
            15
        ),
        new Day24.Group(
            7,
            Day24.GroupType.INFECTION,
            347,
            29661,
            [],
            [],
            170,
            'bludgeoning',
            17
        ),
        new Day24.Group(
            8,
            Day24.GroupType.INFECTION,
            418,
            17587,
            ['radiation', 'cold'],
            ['fire', 'slashing'],
            73,
            'bludgeoning',
            6
        ),
        new Day24.Group(
            9,
            Day24.GroupType.INFECTION,
            2656,
            49851,
            [],
            [],
            32,
            'radiation',
            9
        ),
        new Day24.Group(
            10,
            Day24.GroupType.INFECTION,
            5365,
            35984,
            [],
            [],
            13,
            'radiation',
            13
        )
    ]
}
