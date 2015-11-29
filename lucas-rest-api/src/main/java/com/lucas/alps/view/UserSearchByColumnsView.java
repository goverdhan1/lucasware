package com.lucas.alps.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.UserSearchByColumnsDetailsView;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.entity.user.WmsUser;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.util.DateUtils;

public class UserSearchByColumnsView {

	private List<User> userList;
	private Long totalRecords;

	public UserSearchByColumnsView() {

	}

	public UserSearchByColumnsView(List<User> userList) {
		this.userList = userList;
	}

	@JsonView(UserSearchByColumnsDetailsView.class)
	public Map<String, GridColumnView> getGrid() throws IOException {
		LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
		for (int i = 1; i <= BaseView.TOTAL_NO_OF_GRID_COLUMNS; i++) {
			GridColumn gridColumn = new GridColumn();
			gridView.put(i + "", new GridColumnView(gridColumn));
		}

		for (User user : CollectionsUtilService.nullGuard(userList)) {

			gridView.get("1").getValues().add(user.getUserName());

			gridView.get("2").getValues().add((user.getWmsUser()!= null &&  user.getWmsUser().getHostLogin() != null) ? user.getWmsUser().getHostLogin()  : BaseView.ZERO_LENGTH_STRING);

			gridView.get("3").getValues().add((user.getFirstName() != null) ? user.getFirstName() : BaseView.ZERO_LENGTH_STRING);

			gridView.get("4").getValues().add((user.getLastName() != null) ? user.getLastName()	: BaseView.ZERO_LENGTH_STRING);

			gridView.get("5").getValues().add(user.getSkill());

			// nullable field
			gridView.get("6").getValues().add((user.getShift() != null) ? user.getShift().getShiftName().toString() : BaseView.ZERO_LENGTH_STRING);

            gridView.get("7").getValues().add((user.getJ2uLanguage())!=null ? user.getJ2uLanguage().getLanguageCode(): BaseView.ZERO_LENGTH_STRING);
            gridView.get("8").getValues().add((user.getU2jLanguage())!=null ? user.getU2jLanguage().getLanguageCode(): BaseView.ZERO_LENGTH_STRING);
            gridView.get("9").getValues().add((user.getHhLanguage()) !=null ? user.getHhLanguage().getLanguageCode(): BaseView.ZERO_LENGTH_STRING) ;
            gridView.get("10").getValues().add((user.getAmdLanguage()) !=null ? user.getAmdLanguage().getLanguageCode(): BaseView.ZERO_LENGTH_STRING);

			gridView.get("11").getValues().add(user.isEnable() ? "1" : "0");

			gridView.get("12").getValues().add((user.getEmployeeNumber() != null) ? user.getEmployeeNumber() : BaseView.ZERO_LENGTH_STRING);

			gridView.get("13").getValues().add((user.getTitle() != null) ? user.getTitle() : BaseView.ZERO_LENGTH_STRING);

			gridView.get("14").getValues().add((user.getStartDate() != null) ? DateUtils.formatDate(user.getStartDate()) : BaseView.ZERO_LENGTH_STRING);

			gridView.get("15").getValues().add((user.getBirthDate() != null) ? DateUtils.formatDate(user.getBirthDate()) : BaseView.ZERO_LENGTH_STRING);

			gridView.get("16").getValues().add((user.getMobileNumber() != null) ? user.getMobileNumber() : BaseView.ZERO_LENGTH_STRING);

			gridView.get("17").getValues().add((user.getEmailAddress() != null) ? user.getEmailAddress() : BaseView.ZERO_LENGTH_STRING);

			gridView.get("18").getValues().add(user.getUserId() + BaseView.ZERO_LENGTH_STRING);

			gridView.get("19").getValues().add((user.getUserPreferences() != null) ? user.getUserPreferences().getDataRefreshFrequency().toString() : BaseView.ZERO_LENGTH_STRING);
		}

		return gridView;
	}

	public void setGrid(Map<String, GridColumnView> gridView)
			throws ParseException {
		int pageSize = gridView.get("1").getValues().size();
		userList = new ArrayList<User>();

		for (int i = 0; i < pageSize; i++) {
			userList.add(new User());
			userList.get(i).setUserName(gridView.get("1").getValues().get(i));
			
			WmsUser wmsUser = new WmsUser();
			wmsUser.setHostLogin(gridView.get("2").getValues().get(i));
			
			userList.get(i).setWmsUser(wmsUser);
			userList.get(i).setFirstName(gridView.get("3").getValues().get(i));
			userList.get(i).setLastName(gridView.get("4").getValues().get(i));
			userList.get(i).setSkill(gridView.get("5").getValues().get(i));

			Shift shift = new Shift();
			shift.setShiftName(gridView.get("6").getValues().get(i));
			userList.get(i).setShift(shift);

            final SupportedLanguage j2ulanguage = new SupportedLanguage();
            j2ulanguage.setLanguageCode(gridView.get("7").getValues().get(i));
            userList.get(i).setJ2uLanguage(j2ulanguage);

            final SupportedLanguage u2jlanguage = new SupportedLanguage();
            u2jlanguage.setLanguageCode(gridView.get("8").getValues().get(i));
            userList.get(i).setU2jLanguage(u2jlanguage);

            final SupportedLanguage hhlanguage = new SupportedLanguage();
            hhlanguage.setLanguageCode(gridView.get("9").getValues().get(i));
            userList.get(i).setHhLanguage(hhlanguage);

            final SupportedLanguage amdlanguage = new SupportedLanguage();
            amdlanguage.setLanguageCode(gridView.get("10").getValues().get(i));
            userList.get(i).setAmdLanguage(amdlanguage);

            
            userList.get(i).setEnable(new Boolean(gridView.get("11").getValues().get(i)).booleanValue());
			userList.get(i).setEmployeeNumber(gridView.get("12").getValues().get(i));
			userList.get(i).setTitle(gridView.get("13").getValues().get(i));
			userList.get(i).setStartDate(DateUtils.parseDate(gridView.get("14").getValues().get(i)));

			userList.get(i).setBirthDate(DateUtils.parseDate(gridView.get("15").getValues().get(i)));
			userList.get(i).setMobileNumber(gridView.get("16").getValues().get(i));

			userList.get(i).setEmailAddress(gridView.get("17").getValues().get(i));

		}

	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

  /**
   * @return the totalRecords
   */
  @JsonView(UserSearchByColumnsDetailsView.class)
  public Long getTotalRecords() {
    return totalRecords;
  }

  /**
   * @param totalRecords the totalRecords to set
   */
  public void setTotalRecords(Long totalRecords) {
    this.totalRecords = totalRecords;
  }

}
