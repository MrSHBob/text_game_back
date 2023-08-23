package game.textgameserver.db;

import game.textgameserver.dao.ButtonAction;
import game.textgameserver.dao.PlotButton;
import game.textgameserver.dao.Screen;
import game.textgameserver.dao.User;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import java.util.List;


public class UserRepoTests extends DaoRequests {

    void before() {
        errors = new StringBuilder();
    }
    void after() {
        if ((errors != null) && (!errors.isEmpty())) {
            Assert.assertEquals("", errors.toString());
        }
    }

//    @Test
//    @Order(1)
//    public void saveAndGetUser() {
//        before();
//
//        User u1 = createUser("usr01");
//        User u2 = getUserById(u1.getId());
//
//        if (u1.getCreated() != u2.getCreated()) errors.append("Error in Created: <<" + u1 + ">>\n<<" + u2 + ">>\n");
//        if (u1.getUsername() != u2.getUsername()) errors.append("Error in Username: <<" + u1 + ">>\n<<" + u2 + ">>\n");
//        if (u1.getPassword() != u2.getPassword()) errors.append("Error in Password: <<" + u1 + ">>\n<<" + u2 + ">>\n");
//
//        after();
//        System.out.println();
//    }

    @Test
    @Order(2)
    public void writeTheGame() {
        before();

        writeBaseGameData();

        List<User> users = getAllUsers();
        List<Screen> screens = getAllScreens();
        List<ButtonAction> actions = getAllActions();
        List<ButtonAction> simpleActions = getSimpleActions();

        after();
        System.out.println();
    }

    private void writeBaseGameData() {
        User u11 = createUser("usr11");

        Screen scr1 = createScreen(
                "scr1",
                "default",
                "Hello, create your character. Pick race firstly.",
                "https://sun9-7.userapi.com/impg/G-_jYGjJye6M-BOnqoRuWPqmWc1bFo8Hrmf68Q/8Xs-rxhCqkY.jpg?size=896x505&quality=95&sign=61340ffd4bccf650cd2f716edf08637c&type=album");
        Screen scr2 = createScreen(
                "scr2",
                "",
                "Pick your class.",
                "https://sun9-77.userapi.com/impg/JUk6C5YXy3zJ8336SPrp6Ibci6WYUwsjKzw8tg/WlPLRvfyOh8.jpg?size=680x436&quality=95&sign=33a457a5d4ec8c6cc9e03998c3cbe3b0&type=album");
        Screen scr3 = createScreen(
                "scr3",
                "",
                "Pick your best attribute.",
                "");
        Screen scr4 = createScreen(
                "scr4",
                "",
                "Pick your second best attribute.",
                "");
        Screen scr5 = createScreen(
                "scr5",
                "",
                "You are {TODO}, your character created.",
                ""
        );
        Screen scr6 = createScreen(
                "scr6",
                "",
                "You wake up in a cave, rays of light fall down through a hole in the ceiling.",
                "https://sun9-35.userapi.com/impg/7R4CCFQbGR4e7TxtTZzOappQBOctSTRUD0-hRw/qZxWuGHgXQ0.jpg?size=867x493&quality=95&sign=5a5bf75bb95ba4bc0ce70043b33a7307&type=album");
        Screen scr7 = createScreen(
                "scr7",
                "",
                "In the cave you found something.",
                "https://sun9-35.userapi.com/impg/7R4CCFQbGR4e7TxtTZzOappQBOctSTRUD0-hRw/qZxWuGHgXQ0.jpg?size=867x493&quality=95&sign=5a5bf75bb95ba4bc0ce70043b33a7307&type=album");
        Screen scr7a = createScreen(
                "scr7a",
                "",
                "You falling down.",
                "https://sun9-79.userapi.com/impg/ws5IkVI0AT8ZpNufIC6lsqkBc7nFtLT8o1a2Xg/HQ2dYneiXsI.jpg?size=490x515&quality=95&sign=0a74a9172279ae7241fca588fc114553&type=album");
        Screen scr8 = createScreen(
                "scr8",
                "",
                "You reached the outer world. This is the end of game. Congratulations.",
                "https://sun9-22.userapi.com/impg/hDL-jVCyEcBJbLwYIeTskkFCs5obDJ8mj5icsw/oA4Yt9gemws.jpg?size=901x611&quality=95&sign=162d2219dc2fb78f177c83192aec888c&type=album");
        Screen scr9 = createScreen(
                "scr9",
                "",
                "In the cave you found something.",
                "https://sun9-35.userapi.com/impg/7R4CCFQbGR4e7TxtTZzOappQBOctSTRUD0-hRw/qZxWuGHgXQ0.jpg?size=867x493&quality=95&sign=5a5bf75bb95ba4bc0ce70043b33a7307&type=album");
        Screen scr10 = createScreen(
                "scr10",
                "",
                "You are dead.",
                "https://sun9-26.userapi.com/impg/tjbIUb8ydh8rt2XsxRrt27PnHaSynT_GBGuBVw/0SV8JwcpST8.jpg?size=472x440&quality=95&sign=d902fdc5e10c1e99ccab86f9e32adc22&type=album");

        ButtonAction btnAction1 = createButtonAction(
                "",
                "010",
                "",
                "GT",
                ""
        );
        ButtonAction btnAction2 = createButtonAction(
                "",
                "010",
                "",
                "MSG",
                ""
        );
        ButtonAction btnAction3 = createButtonAction(
                "",
                "020",
                "",
                "IA",
                ""
        );
        ButtonAction btnAction4 = createButtonAction(
                "",
                "020",
                "",
                "DA",
                ""
        );
        ButtonAction btnAction5 = createButtonAction(
                "",
                "010",
                "",
                "IAS",
                ""
        );
        ButtonAction btnAction6 = createButtonAction(
                "",
                "010",
                "",
                "DAS",
                ""
        );
        ButtonAction btnAction7 = createButtonAction(
                "",
                "010",
                "",
                "IAA",
                ""
        );
        ButtonAction btnAction8 = createButtonAction(
                "",
                "010",
                "",
                "DAA",
                ""
        );
        ButtonAction btnAction9 = createButtonAction(
                "",
                "010",
                "",
                "IAE",
                ""
        );
        ButtonAction btnAction10 = createButtonAction(
                "",
                "010",
                "",
                "DAE",
                ""
        );
        ButtonAction btnAction11 = createButtonAction(
                "",
                "010",
                "",
                "IAI",
                ""
        );
        ButtonAction btnAction12 = createButtonAction(
                "",
                "010",
                "",
                "DAI",
                ""
        );
        ButtonAction btnAction13 = createButtonAction(
                "",
                "111",
                "END>",
                "GT",
                "GT"
        );
        ButtonAction btnAction14 = createButtonAction(
                "",
                "111",
                "AGI>",
                "GT",
                "GT"
        );
        ButtonAction btnAction15 = createButtonAction(
                "",
                "111",
                "STR>",
                "GT",
                "MSG"
        );
        ButtonAction btnAction16 = createButtonAction(
                "",
                "311",
                "",
                "GT",
                "GT"
        );
        ButtonAction btnAction17 = createButtonAction(
                "",
                "020",
                "",
                "SET",
                ""
        );

        PlotButton btn1 = createButton(
                scr1.getId(),
                "Orc",
                "",
                "IAS:2;SET:Race/Orc;GT:" + scr2.getId(),
                "Orc have additional strength"
        );
        PlotButton btn2 = createButton(
                scr1.getId(),
                "Elf",
                "",
                "IAA:2;SET:Race/Elf;GT:" + scr2.getId(),
                "Elf have additional agility"
        );
        PlotButton btn3 = createButton(
                scr1.getId(),
                "Human",
                "",
                "IAI:1;IAE:1;SET:Race/Human;GT:" + scr2.getId(),
                "Orc have additional intelligence and endurance"
        );
        PlotButton btn4 = createButton(
                scr2.getId(),
                "Warrior",
                "",
                "IAS:1;IAE:1;SET:Class/Warrior;GT:" + scr3.getId(),
                "Warrior have additional strength and endurance"
        );
        PlotButton btn5 = createButton(
                scr2.getId(),
                "Priest",
                "",
                "IAI:1;IAE:1;SET:Class/Priest;GT:" + scr3.getId(),
                "Priest have additional intelligence and endurance"
        );
        PlotButton btn6 = createButton(
                scr2.getId(),
                "Rogue",
                "",
                "IAA:1;IAE:1;SET:Class/Rogue;GT:" + scr3.getId(),
                "Rogue have additional agility and endurance"
        );

        PlotButton btn7 = createButton(
                scr3.getId(),
                "Strength",
                "",
                "IAS:2;GT:" + scr4.getId(),
                "Strength is you first best attribute"
        );
        PlotButton btn8 = createButton(
                scr3.getId(),
                "Agility",
                "",
                "IAA:2;GT:" + scr4.getId(),
                "Agility is you first best attribute"
        );
        PlotButton btn9 = createButton(
                scr3.getId(),
                "Endurance",
                "",
                "IAE:2;GT:" + scr4.getId(),
                "Endurance is you first best attribute"
        );
        PlotButton btn10 = createButton(
                scr3.getId(),
                "Intelligence",
                "",
                "IAI:2;GT:" + scr4.getId(),
                "Intelligence is you first best attribute"
        );

        PlotButton btn11 = createButton(
                scr4.getId(),
                "Strength",
                "",
                "IAS:1;GT:" + scr5.getId(),
                "Strength is you second best attribute"
        );
        PlotButton btn12 = createButton(
                scr4.getId(),
                "Agility",
                "",
                "IAA:1;GT:" + scr5.getId(),
                "Agility is you second best attribute"
        );
        PlotButton btn13 = createButton(
                scr4.getId(),
                "Endurance",
                "",
                "IAE:1;GT:" + scr5.getId(),
                "Endurance is you second best attribute"
        );
        PlotButton btn14 = createButton(
                scr4.getId(),
                "Intelligence",
                "",
                "IAI:1;GT:" + scr5.getId(),
                "Intelligence is you second best attribute"
        );
        PlotButton btn15 = createButton(
                scr5.getId(),
                "Next",
                "",
                "GT:" + scr6.getId(),
                ""
        );
        PlotButton btn16 = createButton(
                scr6.getId(),
                "Go up through hole.",
                "",
                "ACT:" + btnAction14.getId() + "/10/" + scr8.getId() + "/" + scr7a.getId(),
                ""
        );
        PlotButton btn16a = createButton(
                scr7a.getId(),
                "Next.",
                "",
                "ACT:" + btnAction13.getId() + "/10/" + scr6.getId() + "/" + scr10.getId(),
                ""
        );
        PlotButton btn17 = createButton(
                scr6.getId(),
                "Look around.",
                "",
                "ACT:" + btnAction16.getId() + "/INT/>/9/" + scr7.getId() + "/" + scr9.getId(),
                ""
        );
        PlotButton btn18 = createButton(
                scr7.getId(),
                "Go up through hole.",
                "",
                "ACT:" + btnAction14.getId() + "/10/" + scr8.getId() + "/" + scr7a.getId(),
                ""
        );
        PlotButton btn19 = createButton(
                scr7.getId(),
                "You see the big rock.",
                "",
                "ACT:" + btnAction15.getId() + "/10/" + scr8.getId() + "/You cant move that rock.",
                "You can move that rock under the hole in ceiling and try to go out."
        );
        PlotButton btn20 = createButton(
                scr7.getId(),
                "you see strange bush.",
                "",
                "GT:" + scr8.getId(),
                "looks like you can crawl through under that bush}"
        );
        PlotButton btn21 = createButton(
                scr9.getId(),
                "Go up through hole.",
                "",
                "ACT:" + btnAction14.getId() + "/10/" + scr8.getId() + "/" + scr7a.getId(),
                ""
        );
        PlotButton btn22 = createButton(
                scr9.getId(),
                "You see the big rock.",
                "",
                "ACT:" + btnAction15.getId() + "/10/" + scr8.getId() + "/You cant move that rock.",
                "You can try to move that rock under the hole in ceiling and try to go out."
        );
    }

}
