import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:data/data.dart';

import 'phonenumber_event.dart';
import 'phonenumber_state.dart';

class PhoneNumberBloc extends Bloc<PhoneNumberEvent, PhoneNumberState> {
  PhoneNumberBloc({
    required loginFlowData,
    required countryRepository,
    required userRepository,
  }) :  _countryRepository = countryRepository,
        _userRepository = userRepository,
        super(PhoneNumberState(loginFlowData: loginFlowData));

  final CountryRepository _countryRepository;

  final UserRepository _userRepository;

  @override
  Stream<PhoneNumberState> mapEventToState(PhoneNumberEvent event) async* {
    if (event is Initialized) {
      yield* _mapInitializedToState(event);
    } else if (event is PhoneTextChanged) {
      yield* _mapPhoneTextChangedToState(event);
    } else if (event is NextButtonClicked) {
      yield* _mapNextButtonClickedToState(event);
    } else if (event is CountryUpdated) {
      yield* _mapCountryUpdatedToState(event);
    }
  }

  Stream<PhoneNumberState> _mapInitializedToState(Initialized event) async* {
    final country = await _countryRepository.defaultCountry();
    yield state.copyWith(
      countryCode: country.code,
      countryFlag: country.imageData
    );
  }

  Stream<PhoneNumberState> _mapPhoneTextChangedToState(PhoneTextChanged event) async* {
    final phoneNumber = event.phoneNumber;
    yield state.copyWith(
      isNextButtonVisible: phoneNumber.isNotEmpty,
      isNextButtonEnabled: phoneNumber.isNotEmpty,
      isErrorViewVisible: false
    );
  }

  Stream<PhoneNumberState> _mapNextButtonClickedToState(NextButtonClicked event) async* {
    yield state.copyWith(isLoadingVisible: true, isNextButtonEnabled: false);

    final phoneNumber = event.phoneNumber;
    final result = await _userRepository.exist(phoneNumber);
    if (result is UserExistResultExist) {
      yield state.copyWith(
        loginFlowData: state.loginFlowData.copyWith(phoneNumber: phoneNumber),
        navigation: PasswordNavigation(),
        isLoadingVisible: false,
        isNextButtonEnabled: true,
        isErrorViewVisible: false
      );
    } else if (result is UserExistResultNotExist) {
      yield state.copyWith(
        isLoadingVisible: false,
        isNextButtonEnabled: true,
        isErrorViewVisible: true
      );
    }
  }

  Stream<PhoneNumberState> _mapCountryUpdatedToState(CountryUpdated event) async* {
    final country = event.country;
    yield state.copyWith(
      countryCode: country.code,
      countryFlag: country.imageData
    );
  }
}
