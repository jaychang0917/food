import 'package:base/base.dart';

import 'landing_event.dart';
import 'landing_state.dart';

class LandingBloc extends Bloc<LandingEvent, LandingState> {
  LandingBloc({
    required this.countryRepository,
  }) : super(LandingState());

  final CountryRepository countryRepository;

  @override
  Stream<LandingState> mapEventToState(LandingEvent event) async* {
    if (event is Initialized) {
      yield* _mapInitializedToState(event);
    } else if (event is PhoneTextFieldTapped) {
      yield* _mapPhoneTextFieldTappedToState(event);
    }
  }

  Stream<LandingState> _mapInitializedToState(Initialized event) async* {
    final country = await countryRepository.defaultCountry();
    yield state.copyWith(country: country);
  }

  Stream<LandingState> _mapPhoneTextFieldTappedToState(PhoneTextFieldTapped event) async* {
    yield state.copyWith(navigation: LoginFlowNavigation());
  }
}
