package ru.yandex.practicum.delivery;

import java.util.List;
import java.util.Scanner;

import ru.yandex.practicum.delivery.model.FragileParcel;
import ru.yandex.practicum.delivery.model.Parcel;
import ru.yandex.practicum.delivery.model.PerishableParcel;
import ru.yandex.practicum.delivery.model.StandardParcel;
import ru.yandex.practicum.delivery.service.DeliveryService;

public class DeliveryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DeliveryService deliveryService = new DeliveryService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readIntInRange("Введите номер действия от 0 до 5:", 0, 5);

            switch (choice) {
                case 1 -> addParcel();
                case 2 -> deliveryService.sendParcels();
                case 3 -> calculateCosts();
                case 4 -> updateTrackableItemsLocation();
                case 5 -> showBoxContents();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение отслеживаемых отправлений");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        try {
            System.out.println("Выберите тип посылки:");
            System.out.println("1 — Стандартная посылка");
            System.out.println("2 — Хрупкая посылка");
            System.out.println("3 — Скоропортящаяся посылка");

            int parcelType = readIntInRange("Введите тип посылки от 1 до 3:", 1, 3);

            String description = readRequiredString("Введите описание посылки:");
            int weight = readPositiveInt("Введите вес посылки:");
            String deliveryAddress = readRequiredString("Введите адрес доставки:");
            int sendDay = readPositiveInt("Введите день отправки:");

            Parcel parcel = switch (parcelType) {
                case 1 -> new StandardParcel(
                        description,
                        weight,
                        deliveryAddress,
                        sendDay
                );
                case 2 -> new FragileParcel(
                        description,
                        weight,
                        deliveryAddress,
                        sendDay
                );
                case 3 -> {
                    int timeToLive = readPositiveInt("Введите срок хранения посылки:");
                    yield new PerishableParcel(
                            description,
                            weight,
                            deliveryAddress,
                            sendDay,
                            timeToLive
                    );
                }
                default -> throw new IllegalStateException("Неизвестный тип посылки.");
            };

            if (deliveryService.addParcel(parcel)) {
                System.out.println("Посылка успешно добавлена.");
            } else {
                System.out.println("Посылка не может быть добавлена: коробка переполнена.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка ввода: " + exception.getMessage());
        }
    }

    private static void calculateCosts() {
        int totalCost = deliveryService.calculateTotalCost();
        System.out.println("Общая стоимость всех доставок: " + totalCost);
    }

    private static void updateTrackableItemsLocation() {
        try {
            String newLocation = readRequiredString("Введите новое местоположение отправлений:");
            deliveryService.updateTrackableItemsLocation(newLocation);
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка ввода: " + exception.getMessage());
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите тип коробки:");
        System.out.println("1 — Коробка стандартных посылок");
        System.out.println("2 — Коробка хрупких посылок");
        System.out.println("3 — Коробка скоропортящихся посылок");

        int boxType = readIntInRange("Введите тип коробки от 1 до 3:", 1, 3);

        List<? extends Parcel> parcels = switch (boxType) {
            case 1 -> deliveryService.getStandardParcels();
            case 2 -> deliveryService.getFragileParcels();
            case 3 -> deliveryService.getPerishableParcels();
            default -> throw new IllegalStateException("Неизвестный тип коробки.");
        };

        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }

        System.out.println("Содержимое коробки:");
        for (Parcel parcel : parcels) {
            System.out.println("- " + parcel.getDescription());
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt);

            try {
                int number = Integer.parseInt(scanner.nextLine());

                if (number < min || number > max) {
                    System.out.println(
                            "Ошибка ввода: число должно быть от " + min +
                                    " до " + max + "."
                    );
                    continue;
                }

                return number;
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка ввода: нужно ввести целое число.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.println(prompt);

            try {
                int number = Integer.parseInt(scanner.nextLine());

                if (number <= 0) {
                    System.out.println("Ошибка ввода: число должно быть положительным.");
                    continue;
                }

                return number;
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка ввода: нужно ввести целое число.");
            }
        }
    }

    private static String readRequiredString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine();

            if (value.isBlank()) {
                System.out.println("Ошибка ввода: значение не может быть пустым.");
                continue;
            }

            return value.trim();
        }
    }
}

