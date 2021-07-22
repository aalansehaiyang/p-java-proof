package course.p14.p7;

/**
 *
 */
public class Client {

    public static void main(String[] args) {

        FoodBehavior foodBehavior = new FoodBehavior();
        LanguageBehavior languageBehavior = new LanguageBehavior();

        ChinesePeopleEntity chinesePeopleEntity = new ChinesePeopleEntity(foodBehavior);
        chinesePeopleEntity.out();

        USAPeopleEntity usaPeopleEntity = new USAPeopleEntity(languageBehavior);
        usaPeopleEntity.out();


    }
}
