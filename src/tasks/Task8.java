package tasks;

import common.Person;
import common.Task;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    //persons.remove(0); // плохо тем, что выбрасывает исключение UnsupportedOperationException из-за попытки изменить неизменяемый список
    return persons.stream().skip(1)
            .map(Person::getFirstName).collect(Collectors.toList()); // отфильтровываем все элементы кроме первого
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // при формировании set дублирующиеся значения и так будут отброшены
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::getId, this::convertPersonToString, (person1, person2)-> person1));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);
  }

  // подсчет количества четных элементов
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    //boolean codeSmellsGood = false;
    //boolean reviewerDrunk = false;

    List<Person> persons1 = new ArrayList<>();
    persons1.add(new Person(1, "Ivan", "Ivanov", "Petrovich", null));
    persons1.add(new Person(2, "Semen", "Petrov", "Victorovich", null));

    List<Person> persons2 = new ArrayList<>();
    persons2.add(new Person(1, "Semen", "Kupchin", "Andreevich", null));
    persons2.add(new Person(2, "Semen", "Petrov", "Victorovich", null));
    persons2.add(new Person(3, "Pavel", "Golubev", "Ivanovich", null));

    List<Person> persons3 = new ArrayList<>();
    persons3.add(new Person(1, "Semen", "Kupchin", "Andreevich", null));
    persons3.add(new Person(2, "Semen", "Petrov", "Victorovich", null));
    persons3.add(new Person(3, "Pavel", "Golubev", "Ivanovich", null));

    // проверяем работу методов getDifferentNames() и getNames()
    boolean checkGetDifferentNames = getDifferentNames(persons2).equals(Set.of("Semen", "Pavel"));

    // проверяем работу методов getPersonsName() и convertPersonToString()
    boolean checkGetPersonsName = getPersonNames(persons3).equals(Map.of(1, "Kupchin Semen Andreevich",
            2, "Petrov Semen Victorovich",3, "Golubev Pavel Ivanovich"));

    boolean checkHasSamePersons = hasSamePersons(persons1, persons2); // проверяем работу метода hasSamePersons()
    boolean checkCountEven = countEven(Stream.of(2, 3, 6, 7, 88, 5)) == 3; // проверяем работу метода countEven()

    return checkGetDifferentNames && checkGetPersonsName && checkHasSamePersons && checkCountEven;
    //return codeSmellsGood || reviewerDrunk;
  }
}
