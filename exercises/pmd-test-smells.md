# Detecting test smells with PMD

In folder [`pmd-documentation`](../pmd-documentation) you will find the documentation of a selection of PMD rules designed to catch test smells.
Identify which of the test smells discussed in classes are implemented by these rules.

Use one of the rules to detect a test smell in one of the following projects:

- [Apache Commons Collections](https://github.com/apache/commons-collections)
- [Apache Commons CLI](https://github.com/apache/commons-cli)
- [Apache Commons Math](https://github.com/apache/commons-math)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

Discuss the test smell you found with the help of PMD and propose here an improvement.
Include the improved test code in this file.

## Answer
1- Identify which of the test smells discussed in classes are implemented by these rules:

Parmis les règles présentes dans le dossier “pmd-documentation”, on reconnaît certains “test smells” vu en cours:
La règle “JUnitTestContainsTooManyAsserts” permet de détecter les “Eager test”.
La règle “JUnitAssertionsShouldIncludeMessage” permet de détecter les “Assertion roulette”.
Ces deux règles permettent notamment de détecter les “Piggyback”.
Les règles “JUnit4TestShouldUseAfterAnnotation” et “JUnit4TestShouldUseBeforeAnnotation” permettent de detecter les “Interacting Tests”.


2- Discuss the test smell you found with the help of PMD and propose here an improvement:

Nous avons appliqué la règle “JUnitTestContainsTooManyAsserts” sur le projet “Apache Commons Lang”.
Nous allons nous intéresser au “test smell” suivant.
 

Lorsqu’on observe ce test on se rend vite compte qu’il y a un très grand nombre d’assertions dans le même test, ce qui rend son analyse fastidieuse en cas d'échec. De plus ce genre de test peut facilement être découpé en plusieurs cas indépendant selon le processeur que l’on souhaite tester.

Réécriture du test:
   @Test
    public void testArchX86() {
        Processor processor = ArchUtils.getProcessor(X86);
        assertEqualsTypeNotNull(Processor.Type.X86, processor);
        assertTrue(processor.isX86());
        assertNotEqualsTypeNotNull(Processor.Type.PPC, processor);
        assertFalse(processor.isPPC());
    }


   @Test
    public void testArchX8664() {
        Processor processor = ArchUtils.getProcessor(X86_64);
        assertEqualsTypeNotNull(Processor.Type.X86, processor);
        assertTrue(processor.isX86());
    }


   @Test
    public void testArchIA6432() {
        Processor processor = ArchUtils.getProcessor(IA64_32);
        assertEqualsTypeNotNull(Processor.Type.IA_64, processor);
        assertTrue(processor.isIA64());
    }

Test de base:
   @Test
    public void testArch() {
        Processor processor = ArchUtils.getProcessor(X86);
        assertEqualsTypeNotNull(Processor.Type.X86, processor);
        assertTrue(processor.isX86());
        assertNotEqualsTypeNotNull(Processor.Type.PPC, processor);
        assertFalse(processor.isPPC());


        processor = ArchUtils.getProcessor(X86_64);
        assertEqualsTypeNotNull(Processor.Type.X86, processor);
        assertTrue(processor.isX86());


        processor = ArchUtils.getProcessor(IA64_32);
        assertEqualsTypeNotNull(Processor.Type.IA_64, processor);
        assertTrue(processor.isIA64());


        processor = ArchUtils.getProcessor(IA64);
        assertEqualsTypeNotNull(Processor.Type.IA_64, processor);
        assertTrue(processor.isIA64());
        assertNotEqualsTypeNotNull(Processor.Type.X86, processor);
        assertFalse(processor.isX86());


        processor = ArchUtils.getProcessor(PPC);
        assertEqualsTypeNotNull(Processor.Type.PPC, processor);
        assertTrue(processor.isPPC());
        assertNotEqualsTypeNotNull(Processor.Type.IA_64, processor);
        assertFalse(processor.isIA64());


        processor = ArchUtils.getProcessor(PPC64);
        assertEqualsTypeNotNull(Processor.Type.PPC, processor);
        assertTrue(processor.isPPC());


        processor = ArchUtils.getProcessor(AARCH_64);
        assertEqualsTypeNotNull(Processor.Type.AARCH_64, processor);
        assertTrue(processor.isAarch64());


        processor = ArchUtils.getProcessor(RISCV_32);
        assertEqualsTypeNotNull(Processor.Type.RISC_V, processor);
        assertTrue(processor.isRISCV());


        processor = ArchUtils.getProcessor(RISCV_64);
        assertEqualsTypeNotNull(Processor.Type.RISC_V, processor);
        assertTrue(processor.isRISCV());
    }




