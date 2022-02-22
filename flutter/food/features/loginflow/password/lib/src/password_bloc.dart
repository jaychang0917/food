import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:data/data.dart';

import 'password_event.dart';
import 'password_state.dart';

class PasswordBloc extends Bloc<PasswordEvent, PasswordState> {
  PasswordBloc({
    required loginFlowData,
    required sessionRepository,
  }) :  _sessionRepository = sessionRepository,
        super(PasswordState(loginFlowData: loginFlowData));

  final SessionRepository _sessionRepository;

  @override
  Stream<PasswordState> mapEventToState(PasswordEvent event) async* {
    if (event is PasswordTextChanged) {
      yield* _mapPasswordTextChangedToState(event);
    } else if (event is NextButtonClicked) {
      yield* _mapNextButtonClickedToState(event);
    }
  }

  Stream<PasswordState> _mapPasswordTextChangedToState(PasswordTextChanged event) async* {
    final password = event.password;
    yield state.copyWith(
      loginFlowData: state.loginFlowData.copyWith(password: password),
      isErrorViewVisible: false,
      isNextButtonEnabled: password.isNotEmpty,
      isNextButtonVisible: password.isNotEmpty
    );
  }

  Stream<PasswordState> _mapNextButtonClickedToState(NextButtonClicked event) async* {
    yield state.copyWith(isLoadingVisible: true, isNextButtonEnabled: false);

    final phoneNumber = state.loginFlowData.phoneNumber;
    final password = state.loginFlowData.password;
    final result = await _sessionRepository.login(phoneNumber, password);
    if (result is SessionLoginResultSuccess) {
      yield state.copyWith(
        navigation: MainNavigation(),
        isLoadingVisible: false,
        isNextButtonEnabled: true,
        isErrorViewVisible: false
      );
    } else if (result is SessionLoginResultInvalidPassword) {
      yield state.copyWith(
        isLoadingVisible: false,
        isNextButtonEnabled: true,
        isErrorViewVisible: true
      );
    }
  }
}
