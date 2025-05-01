package online_market.seller_app.enums;

public enum Category {
    ELECTRONICS(
            "Электроника",
            "https://www.rctest.ru/upload/iblock/66f/66f238de823c9701be5428651ec9bdcc.jpg"
    ),
    BOOKS(
            "Книги",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFSmuwiLlMNs7l8uaPOw12yJ9FB2izSXSPqA&s"
    ),
    CLOTHES(
            "Одежда",
            "https://www.yesasia.ru/wp-content/uploads/2025/03/00-11-1200x675.jpg"
    ),
    HOME(
            "Дом и интерьер",
            "https://mamka.moscow/wp-content/uploads/mebel-igrovoj-komnaty-detej-4727-1-1200x675.jpg"
    ),
    SPORT(
            "Спорт",
            "https://cdn.nur.kz/images/1200x675/c899eec63eae4116.jpeg"
    ),
    CHILDREN(
            "Детям",
            "https://prizma.mgpu.ru/wp-content/uploads/2022/10/boys-girls-using-different-gadgets-home-1200x675.jpg"
    ),
    TOYS(
            "Игрушки",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwQRaF9lVkYMhep3cCwsmuEp0-qQ9zjMjQBA&s"
    ),
    WOMEN(
            "Женщинам",
            "https://www.yesasia.ru/wp-content/uploads/2024/09/113d6851-bf86-4b4a-8c83-add6cbda7772-1200x675.jpg"
    ),
    MEN(
            "Мужчинам",
            "https://you-look.by/wp-content/uploads/2023/07/muzhskie-kostyumy-1-1200x675.jpeg"
    ),
    CAR(
            "Автотовары",
            "https://nashapolsha.pl/wp-content/uploads/2019/08/14717_67591939_112035690143333_2553732608684982272_n-1200x675.jpg"
    ),
    BEAUTY(
            "Красота",
            "https://s16.stc.yc.kpcdn.net/share/i/12/10718745/de-1200x675.jpg"
    ),
    PRODUCTS(
            "Продукты",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAHaQcap3vaItR953GWYaDEs_S439mhj077A&s"
    ),
    HEALTH(
            "Здоровье",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXPw7VsFHtf5iWdvsSEeWAskpRXKjvypgtkw&s"
    ),
    ZOO(
            "Зоотовары",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcsBfOjNKGfFPObLnHOIhUlxdh_XqSYoBC1g&s"
    ),
    JEWELRY(
            "Ювелирные украшения",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSb7KBXWauV34BdO0RpG6BPl2AdpO8XauMgEg&s"
    ),
    REPAIR(
            "Для ремонта",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvw5Yte3idU0LXfF4CPmvk2RFxtfY6izyYQQ&s"
    ),
    MOTORCYCLES(
            "Мототехника",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSNWLsatjAzDNjditAUDZdGS9d7lJBQrxtXw&s"
    ),
    STATIONERY(
            "Концтовары",
            "https://cdn.nur.kz/images/1200x675/923f876e276ea51e.jpeg"
    );

    private final String displayName;
    private final String imagePath;

    Category(String displayName, String imagePath) {
        this.displayName = displayName;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
