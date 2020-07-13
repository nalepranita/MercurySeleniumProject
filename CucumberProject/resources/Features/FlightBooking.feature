Feature: Feature To Test FlighBooking on MercuryTours

  Scenario Outline: Check whether user is able to book Flight
    Given User has Logged into MercuryTours with "<username>" and "<password>"
    When User Enters the Flight Details as "<typeOfFlight>" and "<passengers>" and "<departingFrom>" and "<departingTo>" and "<serviceClass>"
    And Enter Payment Details
    Then Check The FLight Confirmation Details
    And Logout of MercuryTours

    Examples:
				    | username | password | typeOfFlight | passengers | departingFrom | departingTo | serviceClass |
				    | mercury  | mercury  | one way      | 1          | London        | Frankfurt   | economy      |
            | mercury  | mercury  | one way      | 1          | London        | Frankfurt   | economy      |