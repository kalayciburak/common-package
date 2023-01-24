package com.kodlamaio.common.constants;

public class Events {
    public static class Producer {
        public static class Car {
            public static final String Created = "car-created";
            public static final String Updated = "car-updated";
            public static final String Deleted = "car-deleted";
            public static final String RentalCreated = "rental-created-for-car";
            public static final String RentalUpdated = "rental-updated-for-car";
            public static final String RentalDeleted = "rental-deleted-for-car";
        }

        public static class Brand {
            public static final String Updated = "brand-updated";
            public static final String Deleted = "brand-deleted";
        }

        public static class Model {
            public static final String Updated = "model-updated";
            public static final String Deleted = "model-deleted";
        }

        public static class Rental {
            public static final String Created = "rental-created";
            public static final String Updated = "rental-updated";
            public static final String Deleted = "rental-deleted";
            public static final String PaymentReceived = "payment-received";
        }
    }

    public static class Consumer {
        public static class Car {
            public static final String CreateGroupId = "car-create";
            public static final String UpdateGroupId = "car-update";
            public static final String DeleteGroupId = "car-delete";
            public static final String RentalCreateGroupId = "car-rental-create";
            public static final String RentalUpdateGroupId = "car-rental-update";
            public static final String RentalDeleteGroupId = "car-rental-delete";
        }

        public static class Brand {
            public static final String UpdateGroupId = "brand-update";
            public static final String DeleteGroupId = "brand-delete";
        }

        public static class Model {
            public static final String UpdateGroupId = "model-update";
            public static final String DeleteGroupId = "model-delete";
        }

        public static class Rental {
            public static final String CreateGroupId = "rental-create";
            public static final String UpdateGroupId = "rental-update";
            public static final String DeleteGroupId = "rental-delete";
            public static final String PaymentReceivedGroupId = "payment-receive";
        }
    }

    public static class Logs {
        public static class Producer {
            public static class Car {
                public static final String Created = "Car created event => %s";
                public static final String Updated = "Car updated event => %s";
                public static final String Deleted = "Car deleted event => %s";
                public static final String RentalCreated = "Rental created event => %s";
                public static final String RentalUpdated = "Rental updated event => %s";
                public static final String RentalDeleted = "Rental deleted event => %s";
            }

            public static class Brand {
                public static final String Updated = "Brand updated event => %s";
                public static final String Deleted = "Brand deleted event => %s";
            }

            public static class Model {
                public static final String Updated = "Model updated event => %s";
                public static final String Deleted = "Model deleted event => %s";
            }

            public static class Rental {
                public static final String Created = "Rental created event => %s";
                public static final String Updated = "Rental updated event => %s";
                public static final String Deleted = "Rental deleted event => %s";
                public static final String PaymentReceived = "Payment received event => %s";
            }
        }

        public static class Consumer {
            public static class Car {
                public static final String Created = "Car created event consumed: {}";
                public static final String Updated = "Car updated event consumed: {}";
                public static final String Deleted = "Car deleted event consumed: {}";
                public static final String RentalCreated = "Rental created event consumed: {}";
                public static final String RentalUpdated = "Rental updated event consumed: {}";
                public static final String RentalDeleted = "Rental deleted event consumed: {}";
            }

            public static class Brand {
                public static final String Updated = "Brand updated event consumed: {}";
                public static final String Deleted = "Brand deleted event consumed: {}";
            }

            public static class Model {
                public static final String Updated = "Model updated event consumed: {}";
                public static final String Deleted = "Model deleted event consumed: {}";
            }

            public static class Rental {
                public static final String Created = "Rental created event consumed: {}";
                public static final String Updated = "Rental updated event consumed: {}";
                public static final String Deleted = "Rental deleted event consumed: {}";
                public static final String InvoiceCreated = "Invoice created for : {}";
            }
        }
    }
}
