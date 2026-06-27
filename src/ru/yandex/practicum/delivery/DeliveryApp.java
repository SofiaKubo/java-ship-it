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
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Ошибка ввода: выберите пункт меню числом.");
                continue;
            }

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    deliveryService.sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    updateTrackableItemsLocation();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
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

            int parcelType = Integer.parseInt(scanner.nextLine());

            if (parcelType < 1 || parcelType > 3) {
                System.out.println("Неверный тип посылки.");
                return;
            }

            System.out.println("Введите описание посылки:");
            String description = scanner.nextLine();

            System.out.println("Введите вес посылки:");
            int weight = Integer.parseInt(scanner.nextLine());

            System.out.println("Введите адрес доставки:");
            String deliveryAddress = scanner.nextLine();

            System.out.println("Введите день отправки:");
            int sendDay = Integer.parseInt(scanner.nextLine());

            Parcel parcel;

            switch (parcelType) {
                case 1:
                    parcel = new StandardParcel(
                            description,
                            weight,
                            deliveryAddress,
                            sendDay
                    );
                    break;
                case 2:
                    parcel = new FragileParcel(
                            description,
                            weight,
                            deliveryAddress,
                            sendDay
                    );
                    break;
                case 3:
                    System.out.println("Введите срок хранения посылки:");
                    int timeToLive = Integer.parseInt(scanner.nextLine());
                    parcel = new PerishableParcel(
                            description,
                            weight,
                            deliveryAddress,
                            sendDay,
                            timeToLive
                    );
                    break;
                default:
                    System.out.println("Неверный тип посылки.");
                    return;
            }

            if (deliveryService.addParcel(parcel)) {
                System.out.println("Посылка успешно добавлена.");
            } else {
                System.out.println(
                        "Посылка не может быть добавлена: коробка переполнена."
                );
            }
        } catch (NumberFormatException exception) {
            System.out.println(
                    "Ошибка ввода: в числовое поле нужно ввести целое число."
            );
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
            System.out.println("Введите новое местоположение отправлений:");
            String newLocation = scanner.nextLine();

            deliveryService.updateTrackableItemsLocation(newLocation);
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка ввода: " + exception.getMessage());
        }
    }

    private static void showBoxContents() {
        try {
            System.out.println("Выберите тип коробки:");
            System.out.println("1 — Коробка стандартных посылок");
            System.out.println("2 — Коробка хрупких посылок");
            System.out.println("3 — Коробка скоропортящихся посылок");

            int boxType = Integer.parseInt(scanner.nextLine());

            List<? extends Parcel> parcels;

            switch (boxType) {
                case 1:
                    parcels = deliveryService.getStandardParcels();
                    break;
                case 2:
                    parcels = deliveryService.getFragileParcels();
                    break;
                case 3:
                    parcels = deliveryService.getPerishableParcels();
                    break;
                default:
                    System.out.println("Неверный тип коробки.");
                    return;
            }

            if (parcels.isEmpty()) {
                System.out.println("Коробка пуста.");
                return;
            }

            System.out.println("Содержимое коробки:");
            for (Parcel parcel : parcels) {
                System.out.println("- " + parcel.getDescription());
            }
        } catch (NumberFormatException exception) {
            System.out.println("Ошибка ввода: выберите тип коробки числом.");
        }
    }
}

